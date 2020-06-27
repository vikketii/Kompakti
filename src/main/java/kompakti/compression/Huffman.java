package kompakti.compression;

import kompakti.util.BinaryNode;
import kompakti.util.BinaryTree;
import kompakti.util.BitList;
import kompakti.util.Converter;


/**
 * Huffman compression and decompression for byte array.
 */
public class Huffman {
    private final Converter converter;

    public Huffman() {
        converter = new Converter();
    }

    /**
     * Data compression using canonical Huffman coding.
     * Returned array contains codebook, length of orignal data and compressed bytes.
     *
     * @param bytes Bytes to compress.
     * @return Compressed bytes.
     */
    public byte[] compress(byte[] bytes) {
        int[] frequencies = new int[256];
        for (int i = 0; i < bytes.length; i++) {
            frequencies[converter.byteToUnsignedInt(bytes[i])]++;
        }

        BinaryTree binaryTree = new BinaryTree(frequencies);
        BinaryNode root = binaryTree.getRoot();
        String[] dictionary = new String[256];

        fillDictionary(root, dictionary, "");
        convertDictionaryToCanonical(dictionary);
        byte[] compressed = convertToHuffmanCompressed(bytes, dictionary);
        byte[] result = new byte[compressed.length + 256 + 4];

        // Huffman codes
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i] != null) {
                result[i] = (byte) dictionary[i].length();
            }
        }

        // Size of original data
        for (int i = 0; i < 4; i++) {
            int move = (2 - i) * 8;
            byte current = (byte) (bytes.length >> move);
            result[dictionary.length + i] = current;
        }

        // Compressed data
        for (int i = 0; i < compressed.length; i++) {
            result[260 + i] = compressed[i];
        }

        return result;
    }

    /**
     * Decompression for Huffman compressed data.
     * Function expects that data is compressed using compress function found in the same class.
     *
     * @param bytes Bytes to decompress.
     * @return Original bytes.
     */
    public byte[] decompress(byte[] bytes) {
        BitList result = new BitList();
        String[] dictionary = readDictionary(bytes);
        BinaryTree binaryTree = new BinaryTree(dictionary);
        BinaryNode root = binaryTree.getRoot();

        int lengthOfData = 0;

        for (int i = 0; i < 4; i++) {
            lengthOfData += converter.byteToUnsignedInt(bytes[256 + i]) << ((2 - i) * 8);
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

    /**
     * Read Huffman codebook from given bytes.
     * Expects that codebook is at the beginning of array.
     *
     * @param bytes Array containing the dictionary.
     * @return Filled dictionary.
     */
    private String[] readDictionary(byte[] bytes) {
        String[] dictionary = new String[256];

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < bytes[i]; j++) {
                if (j == 0) {
                    dictionary[i] = "";
                }
                dictionary[i] += "0";
            }
        }

        convertDictionaryToCanonical(dictionary);
        return dictionary;
    }

    /**
     * Fill Huffman codebook recursively by traversing binarytree.
     *
     * @param node       Current binarynode.
     * @param dictionary Codebook dictionary to fill.
     * @param path       Traversed path.
     */
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

    /**
     * Compress data with dictionary.
     *
     * @param bytes      Data to compress.
     * @param dictionary Huffman codebook.
     * @return Compressed data.
     */
    private byte[] convertToHuffmanCompressed(byte[] bytes, String[] dictionary) {
        BitList bitList = new BitList();
        for (int i = 0; i < bytes.length; i++) {
            int value = converter.byteToUnsignedInt(bytes[i]);
            for (int j = 0; j < dictionary[value].length(); j++) {
                bitList.addBit(dictionary[value].charAt(j) == '1');
            }
        }
        return converter.changeBitListToByteArray(bitList);
    }

    /**
     * Convert Huffman codebook to canonical codebook.
     *
     * @param dictionary Huffman codebook.
     */
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
