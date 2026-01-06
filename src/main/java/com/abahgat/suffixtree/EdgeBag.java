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
class EdgeBag implements Map<Integer, Edge> {
    private int[] codePoints;
    private Edge[] values;
    private static final int BSEARCH_THRESHOLD = 6;

    @Override
    public Edge put(Integer character, Edge e) {
        int c = character.intValue();
        
        if (codePoints == null) {
            codePoints = new int[0];
            values = new Edge[0];
        }
        int idx = search(c);
        Edge previous = null;

        if (idx < 0) {
            int currsize = codePoints.length;
            int[] copy = new int[currsize + 1];
            System.arraycopy(codePoints, 0, copy, 0, currsize);
            codePoints = copy;
            Edge[] copy1 = new Edge[currsize + 1];
            System.arraycopy(values, 0, copy1, 0, currsize);
            values = copy1;
            codePoints[currsize] = c;
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
        return get(((Integer) maybeCharacter).intValue());  // throws if cast fails.
    }

    public Edge get(int c) {
        int idx = search(c);
        if (idx < 0) {
            return null;
        }
        return values[idx];
    }

    private int search(int c) {
        if (codePoints == null)
            return -1;
        
        if (codePoints.length > BSEARCH_THRESHOLD) {
            return java.util.Arrays.binarySearch(codePoints, c);
        }

        for (int i = 0; i < codePoints.length; i++) {
            if (c == codePoints[i]) {
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
     * A trivial implementation of sort, used to sort codePoints[] and values[] according to the data in codePoints.
     * 
     * It was preferred to faster sorts (like qsort) because of the small sizes (<=36) of the collections involved.
     */
    private void sortArrays() {
        for (int i = 0; i < codePoints.length; i++) {
         for (int j = i; j > 0; j--) {
            if (codePoints[j-1] > codePoints[j]) {
               int swap = codePoints[j];
               codePoints[j] = codePoints[j-1];
               codePoints[j-1] = swap;

               Edge swapEdge = values[j];
               values[j] = values[j-1];
               values[j-1] = swapEdge;
            }
         }
      }
    }
    
    @Override
    public boolean isEmpty() {
        return codePoints == null || codePoints.length == 0;
    }
    
    @Override
    public int size() {
        return codePoints == null ? 0 : codePoints.length;
    }
    
    @Override
    public Set<Map.Entry<Integer, Edge>> entrySet() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public void putAll(Map<? extends Integer, ? extends Edge> m) {
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
