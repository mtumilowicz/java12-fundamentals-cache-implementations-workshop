package lfu;

import java.util.HashMap;

class LFUCache {

    /*
    Approach: Create a class called Node which will be a node of a DoublyLinkedList having key, value, frequency,
          prevNode and nextNode.
          Use a HashMap (keyNodeMap: key -> Node) to handle data access by key.
          Then use a HashMap (freqNodeDLLMap: frequency -> DoublyLinkedList<Node>) to handle frequency.
          Also, maintain a variable (minumumFrequency) which will store the current minimum frequency of keys in cache.
          Thus if we want to add a new key, we just need to find (or create new) the likedlist by its frequency (which is 1),
          add the item to the start of the linked list.
          If cache is full and we need to remove an item, we will get the minimum frequency (minumumFrequency),
          get the appropriate linkedlist from freqNodeDLLMap by it, then remove the last item of that linkedlist.
          Also we'll use the key of that removed item to remove the item from our cache (keyNodeMap).
          If we want to increment the freqneucy of a key, we'll get the node, remove it from its current frequency linked list
          by joining it's prevNode and nextNode (This is why we're using DoublyLinkedList. No need to find a node by traversing
          to remove it. If we have the node, we can just join its previous and next node to remove it.). Then add the node to
          the linkedlist of the new (incremented) frequency.
          Thus, the frequency ranking management will be done in O(1) time.
    
    Complexity analysis: Time: O(1), Space: O(n).

    */


    int capacity;
    HashMap<Integer, Node> cache = new HashMap<>();
    HashMap<Integer, NodeDLinkedList> frequencies = new HashMap<>();
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
        return node.value;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            incrementFrequency(node);
            cache.put(key, node);
        } else {

            Node node = new Node(key, value);                                           //Create new node

            if (cache.size() == capacity) {                                          //Cache is full
                Node removedLastNode = frequencies.get(minimumFrequency)
                        .removeLastNode();                              //Remove LFU node from removedLastNode
                cache.remove(removedLastNode.key);                                 //Remove LFU node from cache
            }

            incrementFrequency(node);                                                   //Add to frequency map
            cache.put(key, node);                                                  //Add to cache

            minimumFrequency = 1;                                                       //Since new node is having freqency as 1,
            //update minumumFrequency to be 1
        }

    }

    private boolean isOnlyMinimum(Node node) {
        NodeDLinkedList oldNodeDLinkedList = frequencies.get(node.frequency);
        return isMinimumFrequency(node) && oldNodeDLinkedList.hasSingleElement();
    }

    private void incrementFrequency(Node node) {

        int oldFrequency = node.frequency;

        if (frequencies.containsKey(oldFrequency)) {
            if (isOnlyMinimum(node)) {
                minimumFrequency++;
            }
            NodeDLinkedList oldNodeDLinkedList = frequencies.get(oldFrequency);
            oldNodeDLinkedList.remove(node);
        }

        int newFrequency = oldFrequency + 1;
        node.frequency = newFrequency;
        NodeDLinkedList newNodeDLinkedList = frequencies.getOrDefault(newFrequency, new NodeDLinkedList());
        newNodeDLinkedList.add(node);
        frequencies.put(newFrequency, newNodeDLinkedList);
    }

    private boolean isMinimumFrequency(Node node) {
        return node.frequency == minimumFrequency;
    }
}

class Node {
    int key;
    int value;
    int frequency;
    Node prev;
    Node next;

    Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class NodeDLinkedList {

    Node head, tail;
    private int length;

    //Add a node to top
    void add(Node node) {

        //Remove old pointers
        node.prev = null;
        node.next = null;

        if (head == null) {                                                               //Empty list
            head = node;
            tail = node;
        } else {
            node.next = head;                                                         //Forward linking
            head.prev = node;                                                         //Backward linking
            head = node;
        }

        length++;
    }

    //Remove a node
    void remove(Node node) {

        if (node == head) {                                                               //Need to remove head node
            if (node == tail) {                                                           //Tail node is the same (list size = 1)
                tail = null;                                                            //Make tail null
            }
            head = head.next;                                                         //Make head point to the next node
        } else {                                                                         //Need to remove later node
            node.prev.next = node.next;                                               //Forward linking

            if (node == tail) {                                                           //Need to remove tail node
                tail = node.prev;                                                     //Point tail to prev node
            } else {
                node.next.prev = node.prev;                                           //Backward linking
            }
        }

        length--;

    }

    //Remove last node
    Node removeLastNode() {

        Node tailNode = tail;

        if (tailNode != null) {                                                         //LastNode exists
            remove(tailNode);
        }
        return tailNode;
    }

    boolean hasSingleElement() {
        return length == 1;
    }
}