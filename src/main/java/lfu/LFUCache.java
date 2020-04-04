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
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.val = value;
            incrementFrequency(node);
            cache.put(key, node);
        } else {
            Node node = new Node(key, value);
            if (cache.size() == capacity) {
                removeLruIfFull();
            }

            incrementFrequency(node);
            cache.put(key, node);

            minimumFrequency = 1;
        }

    }

    private void removeLruIfFull() {
        if (cache.size() == capacity) {
            var list = frequencies.get(minimumFrequency);
            var lru = list.getFirst();
            cache.remove(lru.key);
            list.remove(lru);
        }
    }

    private boolean isOnlyMinimum(Node node) {
        var oldNodeDLinkedList = frequencies.get(node.frequency);
        return isMinimumFrequency(node) && oldNodeDLinkedList.hasSingleElement();
    }

    private void incrementFrequency(Node node) {

        int oldFrequency = node.frequency;

        if (frequencies.containsKey(oldFrequency)) {
            if (isOnlyMinimum(node)) {
                minimumFrequency++;
            }
            var oldNodeDLinkedList = frequencies.get(oldFrequency);
            oldNodeDLinkedList.remove(node);
        }

        int newFrequency = oldFrequency + 1;
        node.frequency = newFrequency;
        var newNodeDLinkedList = frequencies.getOrDefault(newFrequency, new DoubleLinkedList());
        newNodeDLinkedList.addLast(node);
        frequencies.put(newFrequency, newNodeDLinkedList);
    }

    private boolean isMinimumFrequency(Node node) {
        return node.frequency == minimumFrequency;
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

    void moveToEnd(Node node) {
        remove(node);
        addLast(node);
    }

    public boolean hasSingleElement() {
        return leftGuard.next.next == rightGuard;
    }
}