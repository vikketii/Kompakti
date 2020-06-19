package kompakti.compression;

import kompakti.util.BinaryNode;
import kompakti.util.BinaryTree;
import kompakti.util.BitList;
import kompakti.util.Converter;

public class Huffman {
    private Converter converter = new Converter();

    public byte[] compress(byte[] bytes) {
        int[] frequencies = new int[256];
        for (int i = 0; i < bytes.length ; i++) {
            frequencies[bytes[i]+128]++;
        }

        BinaryTree binaryTree = new BinaryTree(frequencies);
        BinaryNode root = binaryTree.getRoot();

        String[] dictionary = new String[256];
        fillDictionary(root, dictionary, "");
//        convertDictionaryToCanonical(dictionary);

        byte[] compressed = convertToHuffman(bytes, dictionary);
        byte[] result = new byte[compressed.length + 128]; // 128 = 256/2, because canonical dictionary fits

        for (int i = 0; i < compressed.length; i++) {
            result[i] = compressed[i];
        }

        return result;
    }

    public byte[] decompress(byte[] bytes) {
        return new byte[1];
    }

    private void fillDictionary(BinaryNode node, String[] dictionary, String path) {
        if (node.isLeaf()) {
            dictionary[node.getValue()] = path;
        } else {
            BinaryNode left = node.getLeft();
            BinaryNode right = node.getRight();
            if (left != null) {
                fillDictionary(left, dictionary, path + "0");
            }
            if (right != null) {
                fillDictionary(right, dictionary, path + "1");
            }
        }
    }

    private byte[] convertToHuffman(byte[] bytes, String[] dictionary) {
        BitList bitList = new BitList();
        for (int i = 0; i < bytes.length; i++) {
            String code = dictionary[bytes[i]];
            for (char c : code.toCharArray()) {
                bitList.add(c == '1');
            }
        }
        return converter.changeBitListToByteArray(bitList);
    }

    private void convertDictionaryToCanonical(String[] dictionary) {

    }
}
