package lru;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class DoubleLinkedListNode {
    public final int val;
    public final int key;
    public DoubleLinkedListNode prev;
    public DoubleLinkedListNode next;

    public DoubleLinkedListNode() {
        key = 0;
        val = 0;
    }

    public DoubleLinkedListNode(int val, int key) {
        this.val = val;
        this.key = key;
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

    void removeFirst() {
        if (isNotEmpty()) {
            remove(leftGuard.next);
        }
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
    private DoubleLinkedList lru = new DoubleLinkedList();
    private int capacity;
    private int size = 0;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        DoubleLinkedListNode node = cache.get(key);
        lru.moveToEnd(node);
        return node.val;
    }

    public void put(int key, int value) {
        removeIfExists(key);
        removeIfFull();
        add(key, value);
    }

    private void add(int key, int value) {
        DoubleLinkedListNode node = new DoubleLinkedListNode(key, value);
        cache.put(key, node);
        lru.addLast(node);
        size++;
    }

    private void removeIfExists(int key) {
        Optional.ofNullable(cache.get(key))
                .ifPresent(node -> {
                    lru.remove(node);
                    cache.remove(node.key);
                });
    }

    private void removeIfFull() {
        if (size == capacity) {
            var first = lru.getFirst();
            cache.remove(first.key);
            lru.removeFirst();
            size--;
        }
    }
}