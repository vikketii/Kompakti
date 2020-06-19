package kompakti.util;

public class BinaryNode {
    private BinaryNode left;
    private BinaryNode right;
    private int frequency;
    private byte value;

    public BinaryNode(byte value, int frequency) {
        this.value = value;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public BinaryNode(BinaryNode left, BinaryNode right, int frequency) {
        this.left = left;
        this.right = right;
        this.frequency = frequency;
        this.value = -1;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public int getFrequency() {
        return frequency;
    }

    public byte getValue() {
        return value;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }
}
