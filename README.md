# java13-fundamentals-cache-implementations-workshop
* https://leetcode.com/problems/lru-cache/
* https://leetcode.com/problems/lfu-cache/
* https://medium.com/@krishankantsinghal/my-first-blog-on-medium-583159139237
* https://medium.com/algorithm-and-datastructure/lfu-cache-in-o-1-in-java-4bac0892bdb3
* https://en.wikipedia.org/wiki/Cache_replacement_policies
* 

## Least recently used (LRU)
* discards the least recently used items first
* operations
    * `get(key)` - get the value if the key exists in the cache, otherwise null
    * `put(key, value)` - set or insert the value if the key is not already present
        * when the cache reached its capacity, it should invalidate the least recently used item before inserting a 
        new item
    * both in `O(N)`
* solution
    * `LinkedHashMap<Integer, Integer>` + overridden `removeEldestEntry` method
    * map + double linked list