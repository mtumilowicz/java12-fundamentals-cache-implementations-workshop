package lfu;

import list.DoublyLinkedList;
import list.Node;

import java.util.HashMap;

class LFUCache<K, V> {

    HashMap<K, Node<K, V>> cache = new HashMap<>();

    HashMap<Integer, DoublyLinkedList<K, V>> frequencies = new HashMap<>();

    int capacity;

    int minimumFrequency = 1;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        Node<K, V> node = cache.get(key);
        incrementFrequency(node);
        return node.val;
    }

    public void put(K key, V value) {
        if (capacity <= 0) return;

        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            node.val = value;
            incrementFrequency(node);
        } else {
            removeLrfuIfFull();
            addNew(key, value);
        }
    }

    private void addNew(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        cache.put(key, node);
        addFrequency(node);
        minimumFrequency = 1;
    }

    private void incrementFrequency(Node<K, V> node) {
        removeFrequency(node);
        node.frequency++;
        addFrequency(node);
        incrementMinimum();
    }

    private void removeLrfuIfFull() {
        if (cache.size() == capacity) {
            var list = frequencies.get(minimumFrequency);
            var lru = list.getFirst();
            cache.remove(lru.key);
            list.remove(lru);
        }
    }

    private void removeFrequency(Node<K, V> node) {
        if (frequencies.containsKey(node.frequency)) {
            frequencies.get(node.frequency).remove(node);
        }
    }

    private void incrementMinimum() {
        if (frequencies.get(minimumFrequency).isEmpty()) {
            frequencies.remove(minimumFrequency);
            minimumFrequency++;
        }
    }

    private void addFrequency(Node<K, V> node) {
        frequencies.computeIfAbsent(node.frequency, ignore -> new DoublyLinkedList<>()).addLast(node);
    }
}

