package kompakti.util;

public class HashMap<K, V> {
    int mapSize = 65536; // 2^16
    Node[] nodes;
    int nodeCount;

    public HashMap() {
        nodes = new Node[mapSize];
        nodeCount = 0;
    }

    public void put(String key, int value) {
        int hash = calculateHashValue(key);
        Node newNode = new Node(key, value, null);

        if (nodes[hash] == null) {
            nodes[hash] = newNode;
        } else {
            Node current = nodes[hash];
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.addNext(newNode);
        }
        nodeCount++;
    }

    public int get(String key) {
        int hash = calculateHashValue(key);
        Node currentNode = nodes[hash];
        while (!currentNode.getKey().equals(key)) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getValue();
    }

    public boolean containsKey(String key) {
        int hash = calculateHashValue(key);
        if (nodes[hash] == null) {
            return false;
        }

        Node currentNode = nodes[hash];
        if (currentNode.getKey().equals(key)) {
            return true;
        }

        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
            if (currentNode.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return nodeCount;
    }

    /**
     * Hashfunction, uses polynomial hashing with constant 31.
     * @param key
     * @return hashValue
     */
    private int calculateHashValue(String key) {
        int hash = 0;
        for (char c : key.toCharArray()) {
            hash = (hash * 31 + c) % mapSize;
        }
        return hash;
    }
}
