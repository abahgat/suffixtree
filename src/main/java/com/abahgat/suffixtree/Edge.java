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

/**
 * Represents an Edge in the Suffix Tree.
 * It has a label and a destination Node
 */
class Edge {
    private String source;
    private int start;
    private int end;
    private Node dest;

    public String getLabel() {
        return source.substring(start, end);
    }

    public void setLabel(String label) {
        this.source = label;
        this.start = 0;
        this.end = label.length();
    }
    
    public void setLabel(String source, int start, int end) {
        this.source = source;
        this.start = start;
        this.end = end;
    }

    public Node getDest() {
        return dest;
    }

    public void setDest(Node dest) {
        this.dest = dest;
    }

    public Edge(String label, Node dest) {
        this.source = label;
        this.start = 0;
        this.end = label.length();
        this.dest = dest;
    }
    
    public Edge(String source, int start, int end, Node dest) {
        this.source = source;
        this.start = start;
        this.end = end;
        this.dest = dest;
    }
    
    public int length() {
        return end - start;
    }
    
    public int codePointAt(int index) {
        return source.codePointAt(start + index);
    }
    
    public int getStart() {
        return start;
    }
    
    public int getEnd() {
        return end;
    }
    
    public String getSource() {
        return source;
    }

    public boolean regionMatches(int toffset, String other, int ooffset, int len) {
        return source.regionMatches(start + toffset, other, ooffset, len);
    }
    
    public boolean startsWith(String prefix) {
        return source.regionMatches(start, prefix, 0, prefix.length());
    }
}
