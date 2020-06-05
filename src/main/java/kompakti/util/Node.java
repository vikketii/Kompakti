package kompakti.util;

public class Node {
    private String key;
    private int value;
    private Node next;

    public Node(String key, int value, Node next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public void addNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
