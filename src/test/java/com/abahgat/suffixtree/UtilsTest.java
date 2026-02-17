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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.abahgat.suffixtree.Utils;

public class UtilsTest {
    
    @ParameterizedTest
    @CsvSource({
        "200 S Main St, 200smainst", 
        "Lakeshore Dr., lakeshoredr", 
        "lake-view, lakeview", 
        "St. Jacob's Cathedral, stjacobscathedral"
    })
    public void testNormalize(String input, String expected) {
        assertEquals(expected, Utils.normalize(input));
    }

    @Test
    public void testGetSubstrings() {
        String in = "banana";
        Set<String> out = Utils.getSubstrings(in);
        var outArr = Set.of("b" , "a", "n", "ba", "an", "na", "ban", "ana", "nan", "bana", "anan", "nana", "banan", "anana", "banana");

        for (String s : outArr) {
            assertTrue(out.remove(s));
        }


        assertTrue(out.isEmpty());
    }

}
