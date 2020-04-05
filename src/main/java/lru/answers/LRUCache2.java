package lru.answers;

import list.DoublyLinkedList;
import list.Node;

import java.util.HashMap;
import java.util.Map;

class LRUCache2<K, V> {

    private Map<K, Node<K, V>> cache = new HashMap<>();

    private DoublyLinkedList<K, V> usageLog = new DoublyLinkedList<>();

    private int capacity;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        Node<K, V> node = cache.get(key);
        usageLog.moveToEnd(node);
        return node.val;
    }

    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            var node = cache.get(key);
            node.val = value;
            usageLog.moveToEnd(node);
        } else {
            removeLruIfFull();
            addNew(key, value);
        }
    }

    private void addNew(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        cache.put(key, node);
        usageLog.addLast(node);
    }

    private void removeLruIfFull() {
        if (cache.size() == capacity) {
            var lru = usageLog.getFirst();
            cache.remove(lru.key);
            usageLog.remove(lru);
        }
    }
}