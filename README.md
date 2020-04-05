# java12-fundamentals-cache-implementations-workshop

* references
    * https://leetcode.com/problems/lru-cache/
    * https://leetcode.com/problems/lfu-cache/
    * https://medium.com/@krishankantsinghal/my-first-blog-on-medium-583159139237
    * https://medium.com/algorithm-and-datastructure/lfu-cache-in-o-1-in-java-4bac0892bdb3
    * https://en.wikipedia.org/wiki/Cache_replacement_policies
    * https://ieftimov.com/post/when-why-least-frequently-used-cache-implementation-golang/
    * https://en.wikipedia.org/wiki/Least_frequently_used
    * https://medium.com/algorithm-and-datastructure/lfu-cache-in-o-1-in-java-4bac0892bdb3

## preface
* goals of this workshop
    * understand concept of LRU cache
    * understand concept of LFU cache
    * implement LRU and LFU cache
    * see how guards are useful in list implementations

* workshop: `lfu.workshop`, `lru.workshop`
    * during implementation use classes from `list`
* answers: `lfu.answers`, `lru.answers`

## least recently used (LRU)
* discards the least recently used items first
* operations
    * `get(key)` - get the value if the key exists in the cache, otherwise null
    * `put(key, value)` - set or insert the value if the key is not already present
        * when the cache reached its capacity, it should invalidate the least recently used item before inserting a 
        new item
    * both in `O(1)`
* solutions
    * `LinkedHashMap<Integer, Integer>` + overridden `removeEldestEntry` method
        * remark: `LinkedHashMap` has two iteration order: access-order or insertion-order
    * map + double linked list
    
## least frequently used 
* is nothing but removing least frequently used item from the cache to put the new data into the cache
* is sometimes combined with a Least Recently Used algorithm and called LRFU
* operations
    * `get(key)` - get the value if the key exists in the cache, otherwise null
    * `put(key, value)` - set or insert the value if the key is not already present
        * when the cache reached its capacity, it should invalidate the least recently used item before inserting a 
        new item
    * both in `O(1)`
* may seem like an intuitive approach to memory management it is not without faults
    * consider an item in memory which is referenced repeatedly for a short period of time and is not accessed again 
    for an extended period of time
    * an explicit LFU system is fairly uncommon; instead, there are hybrids that utilize LFU concepts
* solution
    * cache map, frequency map, frequency list