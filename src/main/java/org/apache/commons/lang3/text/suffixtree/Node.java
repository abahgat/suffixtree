/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3.text.suffixtree;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a node of the generalized suffix tree.
 *
 * <p><b>Note:</b> This class is used only internally by {@link GeneralizedSuffixTree}.</p>
 * @see GeneralizedSuffixTree
 */
class Node {

    /**
     * The payload array used to store the data (indexes) associated with this node.
     * In this case, it is used to store all property indexes.
     * 
     * As it is handled, it resembles an ArrayList: when it becomes full it 
     * is copied to another bigger array (whose size is equals to data.length +
     * INCREMENT).
     * 
     * Originally it was a List<Integer> but it took too much memory, changing
     * it to int[] take less memory because indexes are stored using native
     * types.
     */
    private int[] data;
    
    /**
     * Represents index of the last position used in the data int[] array.
     * 
     * It should always be less than data.length
     */
    private int lastIdx = 0;
    
    /**
     * The starting size of the int[] array containing the payload.
     */
    private static final int START_SIZE = 0;

    /**
     * The increment in size used when the payload array is full
     */
    private static final int INCREMENT = 1;

    /**
     * The set of edges starting from this node
     */
    private CharHashMap<Edge> edges;

    /**
     * The suffix link as described in Ukkonen's paper.
     * if str is the string denoted by the path from the root to this, this.suffix
     * is the node denoted by the path that corresponds to str without the first char.
     */
    private Node suffix;

    /**
     * The total number of <em>different</em> results that are stored in this
     * node and in underlying ones (i.e. nodes that can be reached through paths
     * starting from <tt>this</tt>.
     * 
     * This must be calculated explicitly using computeAndCacheCount
     * @see Node#computeAndCacheCount() 
     */
    private int resultCount = -1;

    public static int count = 0;
    
    /**
     * Creates a new Node.
     */
    protected Node() {
        edges = new CharHashMap<Edge>();
        suffix = null;
        data = new int[START_SIZE];
        count++;
    }

    /**
     * Returns all the indexes associated to this node and its children.
     * @return all the indexes associated to this node and its children
     */
    Collection<Integer> getData() {
        return getData(-1);
    }

    /**
     * Returns the first <tt>numElements</tt> elements from the ones associated to this node.
     *
     * Gets data from the payload of both this node and its children, the string representation
     * of the path to this node is a substring of the one of the children nodes.
     * 
     * @param numElements the number of results to return. Use -1 to get all
     * @return the first <tt>numElements</tt> associated to this node and children
     */
    Collection<Integer> getData(int numElements) {
        Set<Integer> ret = new HashSet<Integer>();
        for (int num : data) {
            ret.add(num);
            if (ret.size() == numElements) {
                return ret;
            }
        }
        // need to get more matches from child nodes. This is what may waste time
        for (Edge e : edges.values()) {
            if (-1 == numElements || ret.size() < numElements) {
                for (int num : e.getDest().getData()) { 
                    // was getData(missingItems), but this is wrong imho as it would prevent getting certain values,
                    // e.g. this node contained indexes 1 & 2, and the numElements is set to 3
                    // when traversing to child nodes, we would only receive 1 result, but when a child node
                    // contains the indexes 1,2 and 3 we would miss the 3
                    ret.add(num);
                    if (ret.size() == numElements) {
                        return ret;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Adds the given <tt>index</tt> to the set of indexes associated with <tt>this</tt>.
     *
     * @param index add the index to the set of indexes
     */
    void addRef(int index) {
        if (contains(index)) {
            return;
        }

        addIndex(index);

        // add this reference to all the suffixes as well
        Node iter = this.suffix;
        while (iter != null) {
            if (iter.contains(index)) {
                break;
            }
            iter.addRef(index);
            iter = iter.suffix;
        }
    }

    /**
     * Tests whether a node contains a reference to the given index.
     * 
     * <b>IMPORTANT</b>: it works because the array is sorted by construction
     * 
     * @param index the index to look for
     * @return true <tt>this</tt> contains a reference to index
     */
    private boolean contains(int index) {
        return Arrays.binarySearch(data, 0, lastIdx, index) >= 0;
    }

    /**
     * Computes the number of results that are stored on this node and on its
     * children, and caches the result.
     * 
     * Performs the same operation on subnodes as well
     * @return the number of results
     */
    protected int computeAndCacheCount() {
        computeAndCacheCountRecursive();
        return resultCount;
    }

    private Set<Integer> computeAndCacheCountRecursive() {
        Set<Integer> ret = new HashSet<Integer>();
        for (int num : data) {
            ret.add(num);
        }
        for (Edge e : edges.values()) {
            for (int num : e.getDest().computeAndCacheCountRecursive()) {
                ret.add(num);
            }
        }
        
        resultCount = ret.size();
        return ret;
    }

    /**
     * Returns the number of results that are stored on this node and on its
     * children.
     * 
     * @see Node#computeAndCacheCount() 
     */
    public int getResultCount() throws IllegalStateException {
        // TODO: needs to invalidate when tree is changed
        if (resultCount == -1) {
            return computeAndCacheCount();
        } else {
            return resultCount;
        }
    }

    void addEdge(char ch, Edge e) {
        edges.put(ch, e);
    }

    Edge getEdge(char ch) {
        return edges.get(ch);
    }

    Collection<Edge> getEdges() {
        return edges.values();
    }

    Node getSuffixLink() {
        return suffix;
    }

    void setSuffix(Node suffix) {
        this.suffix = suffix;
    }

    private void addIndex(int index) {
        if (lastIdx == data.length) {
            int[] copy = new int[data.length + INCREMENT];
            System.arraycopy(data, 0, copy, 0, data.length);
            data = copy;
        }
        data[lastIdx++] = index;
    }
}
