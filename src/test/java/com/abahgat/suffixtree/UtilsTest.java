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

import java.util.Set;

import com.abahgat.suffixtree.Utils;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {
    
    public UtilsTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testNormalize() {
        System.out.println("normalize");

        String[] ins = new String[]{"200 S Main St", "Lakeshore Dr.", "lake-view", "St. Jacob's Cathedral"};
        String[] outs = new String[]{"200smainst", "lakeshoredr", "lakeview", "stjacobscathedral"};

        for (int i = 0; i < ins.length; ++i) {
            String result = Utils.normalize(ins[i]);
            assertEquals(outs[i], result);
        }
    }

    public void testGetSubstrings() {
        System.out.println("getsubstrings");

        String in = "banana";
        Set<String> out = Utils.getSubstrings(in);
        String[] outArr = new String[] { "b" , "a", "n", "ba", "an", "na", "ban", "ana", "nan", "bana", "anan", "nana", "banan", "anana", "banana"};

        for (String s : outArr) {
            assertTrue(out.remove(s));
        }


        assertTrue(out.isEmpty());
    }

}
