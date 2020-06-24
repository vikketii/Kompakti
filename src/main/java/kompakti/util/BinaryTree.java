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

    public BinaryTree(String[] dictionary) {
        root = new BinaryNode((byte) -1, 0);
        BinaryNode current = root;

        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i] != null) {
                String value = dictionary[i];
                for (int j = 0; j < value.length(); j++) {
                    if (value.charAt(j) == '1') {
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
