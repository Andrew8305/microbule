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

import org.junit.Before;
import org.junit.Test;
import org.microbule.spi.JaxrsConfigBuilder;
import org.microbule.test.core.MockObjectTestCase;
import org.microbule.test.core.hello.HelloService;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class DefaultJaxrsConfigBuilderStrategyTest extends MockObjectTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    @Mock
    private JaxrsConfigBuilder<HelloService> configBuilder;

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testBuildProxyConfig() {
        final DefaultJaxrsConfigBuilderStrategy strategy = new DefaultJaxrsConfigBuilderStrategy();
        strategy.buildProxyConfig(configBuilder);

        final InOrder order = inOrder(configBuilder);
        order.verify(configBuilder).serviceName();
        order.verify(configBuilder).withPath("hello", "proxy");
        order.verify(configBuilder).withPath("hello");
        order.verify(configBuilder).withPath("default", "proxy");

        verifyNoMoreInteractions(configBuilder);
    }

    @Test
    public void testBuildServerConfig() {
        final DefaultJaxrsConfigBuilderStrategy strategy = new DefaultJaxrsConfigBuilderStrategy();
        strategy.buildServerConfig(configBuilder);

        final InOrder order = inOrder(configBuilder);
        order.verify(configBuilder).serviceName();
        order.verify(configBuilder).withPath("hello", "server");
        order.verify(configBuilder).withPath("hello");
        order.verify(configBuilder).withPath("default", "server");

        verifyNoMoreInteractions(configBuilder);
    }

    @Before
    public void trainMock() {
        when(configBuilder.withPath(any())).thenReturn(configBuilder);
        when(configBuilder.serviceInterface()).thenReturn(HelloService.class);
        when(configBuilder.serviceName()).thenReturn("hello");
    }
}