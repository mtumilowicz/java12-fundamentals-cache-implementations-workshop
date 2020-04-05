package lfu.workshop;

import list.DoublyLinkedList;
import list.Node;

import java.util.HashMap;

class LFUCacheWorkshop<K, V> {

    HashMap<K, Node<K, V>> cache = new HashMap<>();

    HashMap<Integer, DoublyLinkedList<K, V>> frequencies = new HashMap<>();

    int capacity;

    int minimumFrequency = 1;

    public LFUCacheWorkshop(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key) {
        // if not contains -> null
        // if contains -> increment frequency and return value, hint: incrementFrequency

        return null;
    }

    public void put(K key, V value) {
        // if contains: replace value and increment frequency, hint: incrementFrequency
        // if not: remove least recently used from cache if full, add new value
        // hint: removeLrfuIfFull, addNew
    }

    private void addNew(K key, V value) {
        // create new node, hint: new Node
        // put in cache
        // add as a most recently used, hint: addFrequency
        // initialize minimum frequency to 1, hint: minimumFrequency
    }

    private void incrementFrequency(Node<K, V> node) {
        // remove frequency, hint: removeFrequency
        // increment frequency
        // add frequency, hint: addFrequency
        // increment minimum if needed, hint: incrementMinimumIfNeeded
    }

    private void removeLrfuIfFull() {
        // if cache is full remove least frequently used (if many with same frequency - least recently used from them)
        // hint: cache.size(), capacity
        // hint: frequencies.get, minimumFrequency
        // hint: getFirst
        // hint: cache.remove, remove from minimum frequency
    }

    private void removeFrequency(Node<K, V> node) {
        // if there is frequency - remove node from its list
        // hint: frequencies.containsKey, remove
    }

    private void incrementMinimumIfNeeded() {
        // if list of minimum is empty, remove entry and increment minimum frequency
    }

    private void addFrequency(Node<K, V> node) {
        // add frequency, hint: computeIfAbsent, addLast
    }
}

