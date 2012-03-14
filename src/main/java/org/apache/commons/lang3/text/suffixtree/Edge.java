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

/**
 * Represents an Edge in the Suffix Tree.
 * 
 * <p><b>Note:</b> This class is used only internally by {@link GeneralizedSuffixTree}.</p>
 */
class Edge {

    /** The label of this edge. */
    private String label;
    
    /** The node this edge points to. */
    private Node dest;

    /**
     * Creates a new {@link Edge} instance with the given label and destination {@link Node}.
     *
     * @param label the label
     * @param dest the destination {@link Node}
     */
    public Edge(final String label, final Node dest) {
        setLabel(label);
        this.dest = dest;
    }

    /**
     * Returns the label attached to this edge.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label for this edge to the given value.
     *
     * @param label the new label
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Returns the destination {@link Node} this {@link Edge} is pointing to.
     *
     * @return the destination {@link Node}
     */
    public Node getDest() {
        return dest;
    }

    /**
     * Sets the destination {@link Node} of this {@link Edge}.
     *
     * @param dest the new destination {@link Node}
     */
    public void setDest(final Node dest) {
        this.dest = dest;
    }

}
