package lru.workshop;

import java.util.LinkedHashMap;
import java.util.Map;

class LRUCacheWorkshop<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCacheWorkshop(int capacity) {
        // set iteration order to access-order, hint: proper constructor
        // super(initialCapacity, loadFactor, accessOrder);
        this.capacity = capacity;
    }

    // override removeEldestEntry, hint: capacity
}