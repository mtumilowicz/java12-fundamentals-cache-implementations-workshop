package lru.workshop;

import list.DoublyLinkedList;
import list.Node;

import java.util.HashMap;
import java.util.Map;

class LRUCache2Workshop<K, V> {

    private Map<K, Node<K, V>> cache = new HashMap<>();

    private DoublyLinkedList<K, V> usageLog = new DoublyLinkedList<>();

    private int capacity;

    public LRUCache2Workshop(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key) {
        // if not contains -> null
        // if contains: get, mark as a most recently used, return val
        // hints: cache, usageLog.moveToEnd

        return null;
    }

    public void put(K key, V value) {
        // if contains: replace and mark as a most recently used, hint: cache, usageLog.moveToEnd
        // if not: remove least recently used from cache, add new value
        // hint: removeLruIfFull, addNew
    }

    private void addNew(K key, V value) {
        // create new node, hint: new Node
        // put in cache
        // add as a most recently used, hint: usageLog.addLast
    }

    private void removeLruIfFull() {
        // if cache is full remove least recently used
        // hint: cache.size(), capacity, usageLog.getFirst()
        // hint: cache.remove, usageLog.remove
    }
}