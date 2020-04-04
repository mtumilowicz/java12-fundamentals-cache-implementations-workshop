package lru;

import java.util.HashMap;
import java.util.Map;

class DoubleLinkedListNode {
    public final int key;
    public int val;
    public DoubleLinkedListNode prev;
    public DoubleLinkedListNode next;

    public DoubleLinkedListNode() {
        key = 0;
        val = 0;
    }

    public DoubleLinkedListNode(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

class DoubleLinkedList {
    private final DoubleLinkedListNode leftGuard = new DoubleLinkedListNode();
    private final DoubleLinkedListNode rightGuard = new DoubleLinkedListNode();

    public DoubleLinkedList() {
        leftGuard.next = rightGuard;
        rightGuard.prev = leftGuard;
    }

    DoubleLinkedListNode getFirst() {
        return isNotEmpty() ? leftGuard.next : null;
    }

    void remove(DoubleLinkedListNode node) {
        // because of guards, if node is in the list - node.prev != null, node.next != null
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private boolean isNotEmpty() {
        return leftGuard.next != rightGuard;
    }

    void addLast(DoubleLinkedListNode node) {
        rightGuard.prev.next = node;
        node.prev = rightGuard.prev;
        rightGuard.prev = node;
        node.next = rightGuard;
    }

    void moveToEnd(DoubleLinkedListNode node) {
        remove(node);
        addLast(node);
    }
}

class LRUCache2 {
    private Map<Integer, DoubleLinkedListNode> cache = new HashMap<>();
    private DoubleLinkedList usageLog = new DoubleLinkedList();
    private int capacity;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        DoubleLinkedListNode node = cache.get(key);
        usageLog.moveToEnd(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            var node = cache.get(key);
            node.val = value;
        } else {
            removeLruIfFull();
            add(key, value);
        }
    }

    private void add(int key, int value) {
        DoubleLinkedListNode node = new DoubleLinkedListNode(key, value);
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