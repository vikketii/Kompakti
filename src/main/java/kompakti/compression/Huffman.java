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
            frequencies[converter.byteToUnsignedInt(bytes[i])]++;
        }

        // TODO why is there nodes deeper than 8??
        BinaryTree binaryTree = new BinaryTree(frequencies);
        BinaryNode root = binaryTree.getRoot();

        String[] dictionary = new String[256];

        fillDictionary(root, dictionary, "");
        convertDictionaryToCanonical(dictionary);

        byte[] compressed = convertToHuffman(bytes, dictionary);
//        byte[] result = new byte[compressed.length + 128]; // 128 = 256/2, because canonical dictionary fits
        byte[] result = new byte[compressed.length + 256 + 4]; // 128 = 256/2, because canonical dictionary fits


        // Huffman codes
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i] != null) {
                result[i] = (byte) dictionary[i].length();
            }
        }

        // Size of original data
        for (int i = 0; i < 4; i++) {
            int move = (2-i) * 8;
            byte current = (byte) (bytes.length >> move);
            result[dictionary.length + i] = current;
        }

        // Compressed data
        for (int i = 0; i < compressed.length; i++) {
            result[260 + i] = compressed[i];
        }

        return result;
    }

    public byte[] decompress(byte[] bytes) {
        BitList result = new BitList();
        String[] dictionary = readDictionary(bytes);
        BinaryTree binaryTree = new BinaryTree(dictionary);
        BinaryNode root = binaryTree.getRoot();

        int lengthOfData = 0;

        for (int i = 0; i < 4; i++) {
            lengthOfData += converter.byteToUnsignedInt(bytes[256 + i]) << ((2-i) * 8);
        }

        BitList bitList = new BitList();
        for (int i = 260; i < bytes.length; i++) {
            bitList.addByte(bytes[i]);
        }

        BinaryNode current = root;
        int i = 0;
        while (true) {
            if (result.byteSize() >= lengthOfData) {
                break;
            }

            while (!current.isLeaf()) {
                if (bitList.getBit(i)) {
                    current = current.getRight();
                } else {
                    current = current.getLeft();
                }
                i++;
            }
            result.addByte(current.getValue());
            current = root;
        }


        return converter.changeBitListToByteArray(result);
    }

    private String[] readDictionary(byte[] bytes) {
        String[] dictionary = new String[256];

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < bytes[i]; j++) {
                if (j == 0) { dictionary[i] = ""; }
                dictionary[i] += "0";
            }
        }

        convertDictionaryToCanonical(dictionary);
        return dictionary;
    }

    private void fillDictionary(BinaryNode node, String[] dictionary, String path) {
        if (node.isLeaf()) {
            int nodeValue = converter.byteToUnsignedInt(node.getValue());
            dictionary[nodeValue] = path;
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
            int value = converter.byteToUnsignedInt(bytes[i]);
            for (int j = 0; j < dictionary[value].length(); j++) {
                bitList.addBit(dictionary[value].charAt(j) == '1');
            }
        }
        return converter.changeBitListToByteArray(bitList);
    }


    private void convertDictionaryToCanonical(String[] dictionary) {
        String nextValue = "0";
        for (int i = 1; i < 64; i++) {
            for (int j = 0; j < dictionary.length; j++) {
                if (dictionary[j] != null && dictionary[j].length() == i) {
                    dictionary[j] = nextValue;
                    nextValue = converter.addOneToBitString(nextValue);
                }
            }
            nextValue += "0";
        }
    }

}
