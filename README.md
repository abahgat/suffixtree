# Generalized Suffix Tree
[![Build Status](https://travis-ci.org/abahgat/suffixtree.png?branch=master)](https://travis-ci.org/abahgat/suffixtree)

A Generalized Suffix Tree, based on the Ukkonen's paper "On-line construction of suffix trees"
http://www.cs.helsinki.fi/u/ukkonen/SuffixT1withFigs.pdf

Allows for fast storage and fast(er) retrieval by creating a tree-based index out of a set of strings.
Unlike common suffix trees, which are generally used to build an index out of one (very) long string, a *Generalized Suffix Tree* can be used to build an index over many strings.

Its main operations are `put` and `search`:

* `put` adds the given key to the index, allowing for later retrieval of the given value.
* `search` can be used to retrieve the set of all the values that were put in the index with keys that contain a given input.

In particular, after `put(K, V)`, `search(H)` will return a set containing `V` for any string `H` that is substring of `K`.

The overall complexity of the retrieval operation (`search`) is *O(m)* where *m* is the length of the string to search within the index.

## Differences from the original suffix tree

Although the implementation is based on the original design by Ukkonen, there are a few aspects where it differs significantly.

The tree is composed of a set of nodes and labeled edges. The labels on the edges can have any length as long as it's greater than 0.
The only constraint is that no two edges going out from the same node start with the same character.

Because of this, a given _(startNode, stringSuffix)_ pair can denote a unique path within the tree, and it is the path (if any) that can be composed by sequentially traversing all the edges _(e1, e2, …)_ starting from _startNode_ such that _(e1.label + e2.label + …)_ is equal to the _stringSuffix_.
See the `GeneralizedSuffixTree#search` method for details.

The union of all the edge labels from the root to a given leaf node denotes the set of the strings explicitly contained within the GST.
In addition to those Strings, there are a set of different strings that are implicitly contained within the GST, and it is composed of the strings built by concatenating _e1.label + e2.label + ... + $end_, where _e1, e2, …_ is a proper path and _$end_ is prefix of any of the labels of the edges starting from the last node of the path.

This kind of "implicit path" is important in the testAndSplit method.

## License

This Generalized Suffix Tree is released under the Apache License 2.0

   Copyright 2012 Alessandro Bahgat Shehata

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.