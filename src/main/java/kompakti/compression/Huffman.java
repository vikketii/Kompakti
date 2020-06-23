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

        byte[][] dictionary = new byte[256][2]; // [0] value, [1] length of value
        fillDictionary(root, dictionary, (byte) 0, (byte) 0);
        convertDictionaryToCanonical(dictionary);

        byte[] compressed = convertToHuffman(bytes, dictionary);
//        byte[] result = new byte[compressed.length + 128]; // 128 = 256/2, because canonical dictionary fits
        byte[] result = new byte[compressed.length + 256 + 3]; // 128 = 256/2, because canonical dictionary fits

        // Huffman codes
        for (int i = 0; i < dictionary.length; i++) {
            result[i] = dictionary[i][1];
        }

        // Size of original data
        for (int i = 0; i < 3; i++) {
            int move = (2-i) * 8;
//            int value = bytes.length >> move;
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
        byte[][] dictionary = readDictionary(bytes);
        int lengthOfData = 0;

        for (int i = 0; i < 3; i++) {
            lengthOfData += bytes[256 + i] << ((2-i) * 8);
        }

        BitList bitList = new BitList();
        for (int i = 259; i < bytes.length; i++) {
            bitList.addByte(bytes[i]);
        }

        int current = 0;
        for (int i = 0; i < bitList.size(); i++) {
            if (result.byteSize() >= lengthOfData) {
                break;
            }

            current <<= 1;
            if (bitList.getBit(i)) current++;

            // SUPER SLOW
            for (int j = 0; j < dictionary.length; j++) {
                if (dictionary[j][1] != 0 && dictionary[j][0] == current) {
                    result.addByte((byte) j);
                    current = 0;
                    break;
                }
            }
        }


        return converter.changeBitListToByteArray(result);
    }

    private byte[][] readDictionary(byte[] bytes) {
        byte[][] dictionary = new byte[256][2];

        for (int i = 0; i < 256; i++) {
            dictionary[i][1] = bytes[i];
        }

        convertDictionaryToCanonical(dictionary);
        return dictionary;
    }

    private void fillDictionary(BinaryNode node, byte[][] dictionary, byte path, byte length) {
        if (node.isLeaf()) {
            dictionary[node.getValue()][0] = path;
            dictionary[node.getValue()][1] = length;
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

    private byte[] convertToHuffman(byte[] bytes, byte[][] dictionary) {
        BitList bitList = new BitList();
        for (int i = 0; i < bytes.length; i++) {
            for (int j = dictionary[bytes[i]][1]; j > 0; j--) {
                bitList.addBit((dictionary[bytes[i]][0] & (1 << (j-1))) != 0);
            }
        }
        return converter.changeBitListToByteArray(bitList);
    }


    private void convertDictionaryToCanonical(byte[][] dictionary) {
        byte nextValue = 0;
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
