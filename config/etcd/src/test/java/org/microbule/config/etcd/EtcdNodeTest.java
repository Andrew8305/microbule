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

package org.microbule.config.etcd;

import java.util.ArrayList;

import org.junit.Test;
import org.microbule.test.core.MockObjectTestCase;

public class EtcdNodeTest extends MockObjectTestCase {

    @Test
    public void testConstructor() {
        final EtcdNode node = new EtcdNode("one", false, new ArrayList<>(), "val1");
        assertEquals("one", node.getKey());
        assertFalse(node.isDir());
        assertEquals(new ArrayList<>(), node.getNodes());
        assertEquals("val1", node.getValue());

    }
}