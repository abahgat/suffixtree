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
import junit.framework.TestCase;
import static com.abahgat.suffixtree.Utils.getSubstrings;

public class SuffixTreeTest extends TestCase {

    public static <E> void assertEmpty(Collection<E> collection) {
        assertTrue("Expected empty collection.", collection.isEmpty());
    }

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

    public void testWeirdword() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();

        String word = "cacacato";
        in.put(word, 0);

        /* test that every substring is contained within the tree */
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
    }

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

    public void testBananaAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] {"banana", "bano", "ba"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
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

    public void testAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] {"cacaor" , "caricato", "cacato", "cacata", "caricata", "cacao", "banana"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }
        }
        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
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

    public void testSampleAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] {"libertypike",
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
            "bethesda"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
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
    public void testGetSubstrings() {
        Collection<String> exp = new HashSet<>();
        exp.addAll(Arrays.asList(new String[] {"w", "r", "d", "wr", "rd", "wrd"}));
        Collection<String> ret = getSubstrings("wrd");
        assertTrue(ret.equals(exp));
    }

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

        public void testSupplementaryCharacters() {

            GeneralizedSuffixTree tree = new GeneralizedSuffixTree();

            // "😀😁😂" (Grinning Face, Beaming Face with Smiling Eyes, Face with Tears of Joy)

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

    

        public void testSimplestPossibleSample() {

            GeneralizedSuffixTree suffixTree = new GeneralizedSuffixTree();

            suffixTree.put("a", 0);

            suffixTree.put("ab", 1);

    

            assertEquals(2, suffixTree.search("a").size());

            assertEquals(1, suffixTree.search("b").size());

        }

    

        private static final String[] TEST_TERM_LIST = {

                "tablett",

                "fleischtablett",

                "salz",

                "pfeffer",

                "kämpft",

                "grünen",

        };

    

        public void testSubstringToTermMatching() {

            GeneralizedSuffixTree suffixTree = new GeneralizedSuffixTree();

    

        /*

         * Add in some terms that we know are used in parts descriptions

         * and that we might like to search by substring

         */

            for (int index = 0; index < TEST_TERM_LIST.length; index++) {

                suffixTree.put(TEST_TERM_LIST[index], index);

            }

    

            assertEquals(2, suffixTree.search("tablett").size());

            assertEquals(2, suffixTree.search("blet").size());

            assertEquals(1, suffixTree.search("feff").size());

            assertEquals(1, suffixTree.search("ün").size());

            assertEquals(1, suffixTree.search("äm").size());

        }

    }
