package list;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Node {
    public int key;
    public int val;
    public int frequency;
    public Node prev;
    public Node next;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}
