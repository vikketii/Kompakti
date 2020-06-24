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

        int[][] dictionary = new int[256][2]; // [0] value, [1] length of value

        fillDictionary(root, dictionary, 0,0);
        convertDictionaryToCanonical(dictionary);

        byte[] compressed = convertToHuffman(bytes, dictionary);
//        byte[] result = new byte[compressed.length + 128]; // 128 = 256/2, because canonical dictionary fits
        byte[] result = new byte[compressed.length + 256 + 3]; // 128 = 256/2, because canonical dictionary fits


        // Huffman codes
        for (int i = 0; i < dictionary.length; i++) {
            result[i] = (byte) dictionary[i][1];
        }

        // Size of original data
        for (int i = 0; i < 3; i++) {
            int move = (2-i) * 8;
            byte current = (byte) (bytes.length >> move);
            result[dictionary.length + i] = current;
        }

        // Compressed data
        for (int i = 0; i < compressed.length; i++) {
            result[259 + i] = compressed[i];
        }

        return result;
    }

    public byte[] decompress(byte[] bytes) {
        BitList result = new BitList();
        int[][] dictionary = readDictionary(bytes);
        BinaryTree binaryTree = new BinaryTree(dictionary);
        BinaryNode root = binaryTree.getRoot();

        int lengthOfData = 0;

        for (int i = 0; i < 3; i++) {
            lengthOfData += bytes[256 + i] << ((2-i) * 8);
        }

        BitList bitList = new BitList();
        for (int i = 259; i < bytes.length; i++) {
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
                System.out.println(i);
            }
            result.addByte(current.getValue());
            current = root;
        }


        return converter.changeBitListToByteArray(result);
    }

    private int[][] readDictionary(byte[] bytes) {
        int[][] dictionary = new int[256][2];

        for (int i = 0; i < 256; i++) {
            dictionary[i][1] = bytes[i];
        }

        convertDictionaryToCanonical(dictionary);
        return dictionary;
    }

    private void fillDictionary(BinaryNode node, int[][] dictionary, int path, int length) {
        if (node.isLeaf()) {
            int nodeValue = converter.byteToUnsignedInt(node.getValue());
            dictionary[nodeValue][0] = path;
            dictionary[nodeValue][1] = length;
        } else {
            BinaryNode left = node.getLeft();
            BinaryNode right = node.getRight();
            path <<= 1;
            length++;
            if (left != null) {
                fillDictionary(left, dictionary, path, length);
            }
            if (right != null) {
                path++;
                fillDictionary(right, dictionary, path, length);
            }
        }
    }

    private byte[] convertToHuffman(byte[] bytes, int[][] dictionary) {
        BitList bitList = new BitList();
        for (int i = 0; i < bytes.length; i++) {
            int value = converter.byteToUnsignedInt(bytes[i]);
            for (int j = dictionary[value][1]; j > 0; j--) {
                bitList.addBit((dictionary[value][0] & (1 << (j-1))) != 0);
            }
        }
        return converter.changeBitListToByteArray(bitList);
    }


    private void convertDictionaryToCanonical(int[][] dictionary) {
        int nextValue = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < dictionary.length; j++) {
                if (dictionary[j][1] == i) {
                    dictionary[j][0] = nextValue;
                    nextValue++;
                }
            }
            nextValue <<= 1;
        }
    }

}
