package lfu;

import java.util.HashMap;

class LFUCache {

    int capacity;
    HashMap<Integer, Node> cache = new HashMap<>();
    HashMap<Integer, DoubleLinkedList> frequencies = new HashMap<>();
    int minimumFrequency = 1;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node node = cache.get(key);

        incrementFrequency(node);

        return node.val;
    }

    public void put(int key, int value) {
        if (capacity <= 0) return;

        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.val = value;
            incrementFrequency(node);
            cache.put(key, node);
        } else {
            removeLrfuIfFull();
            add(key, value);
        }

    }

    private void add(int key, int value) {
        Node node = new Node(key, value);
        cache.put(key, node);
        addFrequency(node);
        minimumFrequency = 1;
    }

    private void incrementFrequency(Node node) {
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

    private void removeFrequency(Node node) {
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

    private void addFrequency(Node node) {
        frequencies.computeIfAbsent(node.frequency, ignore -> new DoubleLinkedList()).addLast(node);
    }
}

class Node {
    public final int key;
    public int val;
    public int frequency;
    public Node prev;
    public Node next;

    public Node() {
        key = 0;
        val = 0;
    }

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
        this.frequency = 1;
    }
}

class DoubleLinkedList {
    private final Node leftGuard = new Node();
    private final Node rightGuard = new Node();

    public DoubleLinkedList() {
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

    public boolean isEmpty() {
        return leftGuard.next == rightGuard;
    }
}