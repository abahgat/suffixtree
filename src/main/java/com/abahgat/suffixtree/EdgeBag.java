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
import java.util.Map;
import java.util.Set;

/**
 * A specialized implementation of Map that uses native char types and sorted
 * arrays to keep minimize the memory footprint.
 * Implements only the operations that are needed within the suffix tree context.
 */
class EdgeBag implements Map<Character, Edge> {
    private byte[] chars;
    private Edge[] values;
    private static final int BSEARCH_THRESHOLD = 6;

    @Override
    public Edge put(Character character, Edge e) {
        char c = character.charValue();
        if (c != (char) (byte) c) {
            throw new IllegalArgumentException("Illegal input character " + c + ".");
        }
        
        if (chars == null) {
            chars = new byte[0];
            values = new Edge[0];
        }
        int idx = search(c);
        Edge previous = null;

        if (idx < 0) {
            int currsize = chars.length;
            byte[] copy = new byte[currsize + 1];
            System.arraycopy(chars, 0, copy, 0, currsize);
            chars = copy;
            Edge[] copy1 = new Edge[currsize + 1];
            System.arraycopy(values, 0, copy1, 0, currsize);
            values = copy1;
            chars[currsize] = (byte) c;
            values[currsize] = e;
            currsize++;
            if (currsize > BSEARCH_THRESHOLD) {
                sortArrays();
            }
        } else {
            previous = values[idx];
            values[idx] = e;
        }
        return previous;
    }
    
    @Override
    public Edge get(Object maybeCharacter) {
        return get(((Character) maybeCharacter).charValue());  // throws if cast fails.
    }

    public Edge get(char c) {
        if (c != (char) (byte) c) {
            throw new IllegalArgumentException("Illegal input character " + c + ".");
        }
        
        int idx = search(c);
        if (idx < 0) {
            return null;
        }
        return values[idx];
    }

    private int search(char c) {
        if (chars == null)
            return -1;
        
        if (chars.length > BSEARCH_THRESHOLD) {
            return java.util.Arrays.binarySearch(chars, (byte) c);
        }

        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Collection<Edge> values() {
        return Arrays.asList(values == null ? new Edge[0] : values);
    }
    
    /**
     * A trivial implementation of sort, used to sort chars[] and values[] according to the data in chars.
     * 
     * It was preferred to faster sorts (like qsort) because of the small sizes (<=36) of the collections involved.
     */
    private void sortArrays() {
        for (int i = 0; i < chars.length; i++) {
         for (int j = i; j > 0; j--) {
            if (chars[j-1] > chars[j]) {
               byte swap = chars[j];
               chars[j] = chars[j-1];
               chars[j-1] = swap;

               Edge swapEdge = values[j];
               values[j] = values[j-1];
               values[j-1] = swapEdge;
            }
         }
      }
    }
    
    @Override
    public boolean isEmpty() {
        return chars == null || chars.length == 0;
    }
    
    @Override
    public int size() {
        return chars == null ? 0 : chars.length;
    }
    
    @Override
    public Set<Map.Entry<Character, Edge>> entrySet() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public Set<Character> keySet() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public void putAll(Map<? extends Character, ? extends Edge> m) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public Edge remove(Object key) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public boolean containsValue(Object key) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
