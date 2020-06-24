package kompakti.util;


public class BinaryTree {
    BinaryNode root;

    public BinaryTree(int[] frequencies) {
        PriorityQueue queue = new PriorityQueue();

        for (int i = 0; i < 256 ; i++) {
            if (frequencies[i] > 0) {
                queue.add(new BinaryNode((byte) i, frequencies[i]));
            }
        }

        while (queue.size() > 1) {
            BinaryNode a = queue.poll();
            BinaryNode b = queue.poll();
            queue.add(new BinaryNode(a, b, a.getFrequency() + b.getFrequency()));
        }

        this.root = queue.poll();
    }

    public BinaryTree(int[][] dictionary) {
        root = new BinaryNode((byte) -1, 0);
        BinaryNode current = root;

        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i][1] != 0) {
                int value = dictionary[i][0];
                for (int j = dictionary[i][1] - 1; j >= 0; j--) {
                    int direction = value & (1 << j);
                    if (direction != 0) {
                        current = current.getRightOrCreate();
                    } else {
                        current = current.getLeftOrCreate();
                    }
                }
                current.setValue((byte) i);
                current = root;
            }
        }
    }

    public BinaryNode getRoot() {
        return root;
    }
}
