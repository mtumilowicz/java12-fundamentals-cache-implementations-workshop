package lru;

import java.util.HashMap;
import java.util.Map;

class Node {
    public final int key;
    public int val;
    public Node prev;
    public Node next;

    public Node() {
        key = 0;
        val = 0;
    }

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

class DoublyLinkedList {
    private final Node leftGuard = new Node();
    private final Node rightGuard = new Node();

    public DoublyLinkedList() {
        leftGuard.next = rightGuard;
        rightGuard.prev = leftGuard;
    }

    Node getFirst() {
        return isNotEmpty() ? leftGuard.next : null;
    }

    void remove(Node node) {
        // because of guards, if node is in the list - node.prev != null, node.next != null
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private boolean isNotEmpty() {
        return leftGuard.next != rightGuard;
    }

    void addLast(Node node) {
        rightGuard.prev.next = node;
        node.prev = rightGuard.prev;
        rightGuard.prev = node;
        node.next = rightGuard;
    }

    void moveToEnd(Node node) {
        remove(node);
        addLast(node);
    }
}

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
            usageLog.moveToEnd(node);
            node.val = value;
        } else {
            removeLruIfFull();
            add(key, value);
        }
    }

    private void add(int key, int value) {
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