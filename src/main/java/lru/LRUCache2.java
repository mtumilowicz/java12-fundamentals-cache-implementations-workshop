package lru;

import list.DoublyLinkedList;
import list.Node;

import java.util.HashMap;
import java.util.Map;

class LRUCache2 {

    private Map<Integer, Node> cache = new HashMap<>();

    private DoublyLinkedList usageLog = new DoublyLinkedList();

    private int capacity;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node node = cache.get(key);
        usageLog.moveToEnd(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            var node = cache.get(key);
            node.val = value;
            usageLog.moveToEnd(node);
        } else {
            removeLruIfFull();
            addNew(key, value);
        }
    }

    private void addNew(int key, int value) {
        Node node = new Node(key, value);
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