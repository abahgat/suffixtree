package com.abahgat.suffixtree;

import junit.framework.TestCase;

/**
 * Test that {@link GeneralizedSuffixTree#search(String)} returns the expected number of results.
 * <p>
 * When {@link Node} was changed to make building very large trees faster and put
 * less pressure on the garbage collector, a bug in how the internal data was
 * handled was discovered.
 * <p>
 * This class contains tests to ensure that the handling of this array is correct.
 *
 */
public class SearchResultBugTest extends TestCase {

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
