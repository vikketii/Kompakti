package kompakti.util;


public class PriorityQueue {
    private BinaryNode[] heap;
    private int itemCount;

    /**
     * PriorityQueue for BinaryNodes.
     * Minimum heap.
     * Not dynamic.
     */
    public PriorityQueue() {
        this.heap = new BinaryNode[4096];
        itemCount = 0;
    }

    public void add(BinaryNode binaryNode) {
        itemCount++;
        heap[itemCount] = binaryNode;
        makeHeapValidAddition();
    }

    public BinaryNode poll() {
        BinaryNode result = heap[1];
        heap[1] = heap[itemCount];
        heap[itemCount] = null;
        itemCount--;
        makeHeapValidPolling();
        return result;
    }

    public int size() {
        return itemCount;
    }

    private void makeHeapValidAddition() {
        int position = itemCount;
        while (position > 1 && heap[position].compareTo(heap[position / 2]) < 0) {
            BinaryNode parent = heap[position / 2];
            heap[position / 2] = heap[position];
            heap[position] = parent;
            position /= 2;
        }
    }

    private void makeHeapValidPolling() {
        int position = 1;
        while (position * 2 < itemCount) {
            int childPosition = position * 2;

            if (childPosition + 1 < itemCount && heap[childPosition].compareTo(heap[childPosition + 1]) > 0) {
                childPosition++;
            }

            if (heap[position].compareTo(heap[childPosition]) < 0) {
                break;
            }

            BinaryNode child = heap[childPosition];
            heap[childPosition] = heap[position];
            heap[position] = child;

            position = childPosition;
        }
    }

    @Override
    public String toString() {
        if (itemCount == 0) { return "[]"; }

        String result = "[";
        for (int i = 1; i < itemCount; i++) {
            result += heap[i].getFrequency() + ",";
        }
        result += heap[itemCount].getFrequency() + "]";
        return result;
    }
}
