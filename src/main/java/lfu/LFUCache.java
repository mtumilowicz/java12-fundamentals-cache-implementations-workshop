package lfu;

import list.DoublyLinkedList;
import list.Node;

import java.util.HashMap;

class LFUCache {

    HashMap<Integer, Node> cache = new HashMap<>();

    HashMap<Integer, DoublyLinkedList> frequencies = new HashMap<>();

    int capacity;

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
        } else {
            removeLrfuIfFull();
            addNew(key, value);
        }
    }

    private void addNew(int key, int value) {
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
        frequencies.computeIfAbsent(node.frequency, ignore -> new DoublyLinkedList()).addLast(node);
    }
}

