package kompakti.util;

import java.util.PriorityQueue;

public class BinaryTree {
    BinaryNode root;

    public BinaryTree(int[] frequencies) {
        PriorityQueue<BinaryNode> queue = new PriorityQueue<>(
                (node1, node2) -> (node1.getFrequency() < node2.getFrequency()) ? -1 : 1
        );

        for (int i = 0; i < 256 ; i++) {
            if (frequencies[i] > 0) {
                queue.add(new BinaryNode((byte) (i-128), frequencies[i]));
            }
        }

        while (queue.size() > 1) {
            BinaryNode a = queue.poll();
            BinaryNode b = queue.poll();
            queue.add(new BinaryNode(a, b, a.getFrequency() + b.getFrequency()));
        }

        this.root = queue.poll();
    }

    public BinaryNode getRoot() {
        return root;
    }
}
