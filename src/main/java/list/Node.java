package list;

public class Node<K, V> {

    public K key;
    public V val;
    public int frequency;
    public Node<K, V> prev;
    public Node<K, V> next;

    public Node() {
    }

    public Node(K key, V val) {
        this.key = key;
        this.val = val;
    }
}
