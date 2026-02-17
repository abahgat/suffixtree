/**
 * Copyright 2012 Alessandro Bahgat Shehata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abahgat.suffixtree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeBagTest {

    @Test
    public void testPut() {
        EdgeBag bag = new EdgeBag();
        Edge e1 = new Edge("asd", null);
        Edge e2 = new Edge("errimo", null);
        Edge e3 = new Edge("foo", null);
        Edge e4 = new Edge("bar", null);
        Edge e5 = new Edge("\u540d\u79f0", null);
        bag.put((int) 'a', e1);
        bag.put((int) 'e', e2);
        bag.put((int) 'f', e3);
        bag.put((int) 'b', e4);
        bag.put(0x540d, e5);
        assertEquals(5, bag.values().size(), () -> "Bag contains " + bag.values().size() + " elements");
        assertTrue(bag.get('a').equals(e1));
        assertTrue(bag.get('e').equals(e2));
        assertTrue(bag.get('f').equals(e3));
        assertTrue(bag.get('b').equals(e4));
        assertTrue(bag.get(0x540d).equals(e5));
    }

    @Test
    public void testCast() {
        for (char c = '0'; c <= '9'; ++c) {
            assertEquals(c, (char) (byte) c);
        }

        for (char c = 'a'; c <= 'z'; ++c) {
            assertEquals(c, (char) (byte) c);
        }
    }

    public void testSort() {

    }

}