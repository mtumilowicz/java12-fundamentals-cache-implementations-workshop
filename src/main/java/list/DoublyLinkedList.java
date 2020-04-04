package list;

public class DoublyLinkedList<K ,V> {
    private final Node<K ,V> leftGuard = new Node<>();
    private final Node<K ,V> rightGuard = new Node<>();

    public DoublyLinkedList() {
        leftGuard.next = rightGuard;
        rightGuard.prev = leftGuard;
    }

    public Node<K ,V> getFirst() {
        return isNotEmpty() ? leftGuard.next : null;
    }

    public void remove(Node<K ,V> node) {
        // because of guards, if node is in the list - node.prev != null, node.next != null
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void addLast(Node<K ,V> node) {
        rightGuard.prev.next = node;
        node.prev = rightGuard.prev;
        rightGuard.prev = node;
        node.next = rightGuard;
    }

    public void moveToEnd(Node<K ,V> node) {
        remove(node);
        addLast(node);
    }

    public boolean isEmpty() {
        return leftGuard.next == rightGuard;
    }

    private boolean isNotEmpty() {
        return !isEmpty();
    }
}