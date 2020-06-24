package kompakti.util;

public class BinaryNode implements Comparable<BinaryNode> {
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

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getLeftOrCreate() {
        if (this.left == null) {
            setLeft(new BinaryNode((byte) -1, 0));
        }
        return getLeft();
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public BinaryNode getRightOrCreate() {
        if (right == null) {
            setRight(new BinaryNode((byte) -1, 0));
        }
        return getRight();
    }

    public int getFrequency() {
        return frequency;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    @Override
    public int compareTo(BinaryNode other) {
        return this.getFrequency() - other.getFrequency();
    }
}
