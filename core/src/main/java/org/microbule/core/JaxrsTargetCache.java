/*
 * Copyright (c) 2017 The Microbule Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.microbule.core;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.microbule.config.api.Config;
import org.microbule.scheduler.api.SchedulerService;
import org.microbule.scheduler.api.RefreshableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JaxrsTargetCache<T> implements RemovalListener<String, RefreshableReference<T>> {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String TIMEOUT_PROP = "timeout";
    private static final String TIMEOUT_UNIT_PROP = "timeoutUnit";
    private static final String REFRESH_DELAY_PROP = "refreshDelay";
    private static final String REFRESH_DELAY_UNIT_PROP = "refreshDelayUnit";
    private static final long DEFAULT_TIMEOUT = 5;
    private static final TimeUnit DEFAULT_UNIT = TimeUnit.MINUTES;
    private static final long DEFAULT_REFRESH_DELAY = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(JaxrsTargetCache.class);
    private final LoadingCache<String, RefreshableReference<T>> cache;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    JaxrsTargetCache(Function<String, T> factory, SchedulerService schedulerService, Config cacheConfig) {
        final long timeout = cacheConfig.longValue(TIMEOUT_PROP).orElse(DEFAULT_TIMEOUT);
        final TimeUnit timeoutUnit = cacheConfig.enumValue(TIMEOUT_UNIT_PROP, TimeUnit.class).orElse(DEFAULT_UNIT);
        final long refreshDelay = cacheConfig.longValue(REFRESH_DELAY_PROP).orElse(DEFAULT_REFRESH_DELAY);
        final TimeUnit refreshDelayUnit = cacheConfig.enumValue(REFRESH_DELAY_UNIT_PROP, TimeUnit.class).orElse(DEFAULT_UNIT);
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(timeout, timeoutUnit).build(new CacheLoader<String, RefreshableReference<T>>() {
            @Override
            public RefreshableReference<T> load(String address) throws Exception {
                return schedulerService.createRefreshableReference(currentValue -> factory.apply(address), refreshDelay, refreshDelayUnit);
            }
        });
    }

//----------------------------------------------------------------------------------------------------------------------
// RemovalListener Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void onRemoval(RemovalNotification<String, RefreshableReference<T>> notification) {
        LOGGER.debug("Canceling refresh...");
        notification.getValue().cancel();
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    T getTarget(String address) {
        return cache.getUnchecked(address).get();
    }
}
