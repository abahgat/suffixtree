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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.abahgat.suffixtree.Utils.getSubstrings;

public class SuffixTreeTest {

    public static <E> void assertEmpty(Collection<E> collection) {
        assertTrue(collection.isEmpty(), () -> "Expected empty collection.");
    }

    @Test
    public void testBasicTreeGeneration() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();

        String word = "cacao";
        in.put(word, 0);

        /* test that every substring is contained within the tree */
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
        assertEmpty(in.search("caco"));
        assertEmpty(in.search("cacaoo"));
        assertEmpty(in.search("ccacao"));

        in = new GeneralizedSuffixTree();
        word = "bookkeeper";
        in.put(word, 0);
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
        assertEmpty(in.search("books"));
        assertEmpty(in.search("boke"));
        assertEmpty(in.search("ookepr"));
    }

    @Test
    public void testWeirdword() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();

        String word = "cacacato";
        in.put(word, 0);

        /* test that every substring is contained within the tree */
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
    }

    @Test
    public void testDouble() {
        // test whether the tree can handle repetitions
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String word = "cacao";
        in.put(word, 0);
        in.put(word, 1);

        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
            assertTrue(in.search(s).contains(1));
        }
    }

    @Test
    public void testBananaAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] { "banana", "bano", "ba" };
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);
            final int index = i;
            final String word = words[i];

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull(result, () -> "result null for string " + s + " after adding " + word);
                assertTrue(result.contains(index), () -> "substring " + s + " not found after adding " + word);
            }

        }

        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

    }

    @Test
    public void testAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] { "cacaor", "caricato", "cacato", "cacata", "caricata", "cacao", "banana" };
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);
            final int index = i;
            final String word = words[i];

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull(result, () -> "result null for string " + s + " after adding " + word);
                assertTrue(result.contains(index), () -> "substring " + s + " not found after adding " + word);
            }
        }
        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            final int index = i;
            final String word = words[i];
            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull(result, () -> "result null for string " + s + " after adding " + word);
                assertTrue(result.contains(index), () -> "substring " + s + " not found after adding " + word);
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

        in.computeCount();
        testResultsCount(in.getRoot());

        assertEmpty(in.search("aoca"));
    }

    @Test
    public void testSampleAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] { "libertypike",
                "franklintn",
                "carothersjohnhenryhouse",
                "carothersezealhouse",
                "acrossthetauntonriverfromdightonindightonrockstatepark",
                "dightonma",
                "dightonrock",
                "6mineoflowgaponlowgapfork",
                "lowgapky",
                "lemasterjohnjandellenhouse",
                "lemasterhouse",
                "70wilburblvd",
                "poughkeepsieny",
                "freerhouse",
                "701laurelst",
                "conwaysc",
                "hollidayjwjrhouse",
                "mainandappletonsts",
                "menomoneefallswi",
                "mainstreethistoricdistrict",
                "addressrestricted",
                "brownsmillsnj",
                "hanoverfurnace",
                "hanoverbogironfurnace",
                "sofsavannahatfergusonaveandbethesdard",
                "savannahga",
                "bethesdahomeforboys",
                "bethesda" };
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);
            final int index = i;
            final String word = words[i];

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull(result, () -> "result null for string " + s + " after adding " + word);
                assertTrue(result.contains(index), () -> "substring " + s + " not found after adding " + word);
            }

        }
        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

        in.computeCount();
        testResultsCount(in.getRoot());

        assertEmpty(in.search("aoca"));
    }

    private void testResultsCount(Node n) {
        for (Edge e : n.getEdges().values()) {
            assertEquals(n.getData(-1).size(), n.getResultCount());
            testResultsCount(e.getDest());
        }
    }

    /* testing a test method :) */
    @Test
    public void testGetSubstrings() {
        var exp = Set.of("w", "r", "d", "wr", "rd", "wrd");
        Collection<String> ret = getSubstrings("wrd");
        assertTrue(ret.equals(exp));
    }

    @Test
    public void testUnicode() {
        GeneralizedSuffixTree tree = new GeneralizedSuffixTree();
        // "こんにちは" means "Hello" in Japanese
        String word = "こんにちは";
        tree.put(word, 1);

        Collection<Integer> result = tree.search("んに");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(1));

        result = tree.search("に");
        assertTrue(result.contains(1));

        result = tree.search("は");
        assertTrue(result.contains(1));

        assertEmpty(tree.search("さ")); // 'sa', not in word
    }

    @Test
    public void testSupplementaryCharacters() {

        GeneralizedSuffixTree tree = new GeneralizedSuffixTree();

        // "😀😁😂" (Grinning Face, Beaming Face with Smiling Eyes, Face with Tears of
        // Joy)

        String word = "😀😁😂";

        tree.put(word, 1);

        Collection<Integer> result = tree.search("😁");

        assertNotNull(result);

        assertEquals(1, result.size());

        assertTrue(result.contains(1));

        result = tree.search("😂");

        assertTrue(result.contains(1));
        assertEmpty(tree.search("🤣")); // ROFL, not in word
    }

    @Test
    public void testSimplestPossibleSample() {
        GeneralizedSuffixTree suffixTree = new GeneralizedSuffixTree();
        suffixTree.put("a", 0);
        suffixTree.put("ab", 1);

        assertEquals(2, suffixTree.search("a").size());
        assertEquals(1, suffixTree.search("b").size());
    }

    @Test
    public void testSubstringToTermMatching() {
        GeneralizedSuffixTree suffixTree = new GeneralizedSuffixTree();
        /*
         * Add in some terms that we know are used in parts descriptions
         * and that we might like to search by substring
         */
        String[] terms = {
                "tablett",
                "fleischtablett",
                "salz",
                "pfeffer",
                "kämpft",
                "grünen",
        };

        for (int i = 0; i < terms.length; i++) {
            suffixTree.put(terms[i], i);
        }

        assertEquals(2, suffixTree.search("tablett").size());
        assertEquals(2, suffixTree.search("blet").size());
        assertEquals(1, suffixTree.search("feff").size());
        assertEquals(1, suffixTree.search("ün").size());
        assertEquals(1, suffixTree.search("äm").size());
    }

}
