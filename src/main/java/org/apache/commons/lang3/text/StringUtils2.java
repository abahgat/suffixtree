package org.apache.commons.lang3.text;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.text.suffixtree.GeneralizedSuffixTree;


public class StringUtils2 {

    // lcs - Longest Common Substring
    //-----------------------------------------------------------------------
    
    public static CharSequence lcs(CharSequence str1, CharSequence str2) {
        final Collection<CharSequence> substrings = getLcs(str1, str2);
        if (substrings != null && substrings.size() > 0) {
            return substrings.iterator().next();
        } else {
            return null;
        }
    }

    public static Collection<? extends CharSequence> lcs(CharSequence... strs) {
        if (strs == null || strs.length < 2) {
            return null;
        }

        final GeneralizedSuffixTree tree = new GeneralizedSuffixTree();
        for (CharSequence s : strs) {
            tree.addSequence(s);
        }

        return tree.lcs();
    }
    
    /**
     * TODO: use some heuristic when to use the dynamic programming variant and when the suffix tree impl
     * @param str1
     * @param str2
     * @return
     */
    public static Collection<CharSequence> getLcs(CharSequence str1,
                                                  CharSequence str2) {
        if (str1 == null || str2 == null) {
            return null;
        }
        
        final int m = str1.length();
        final int n = str2.length();
        
        if (m == 0 || n == 0) {
            return null;
        }
        
        int[] curr = new int[n];
        int[] prev = new int[n];
        
        int maxLength = 0;
        
        final Set<CharSequence> ret = new HashSet<CharSequence>();
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    if (i == 0 || j == 0) {
                        curr[j] = 1;
                    } else {
                        curr[j] = prev[j - 1] + 1;
                    }
                    if (maxLength < curr[j]) {
                        maxLength = curr[j];
                        ret.clear();
                    }
                    if (curr[j] == maxLength) {
                        ret.add(str1.subSequence(i - maxLength + 1, i + 1));
                    }
                } else {
                    curr[j] = 0;
                }
            }

            final int[] swap = curr;
            curr = prev;
            prev = swap;
        }
        
        return ret;        
    }


}
