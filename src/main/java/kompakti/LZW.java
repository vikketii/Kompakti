package kompakti;

import kompakti.util.BitList;
import kompakti.util.HashMap;

public class LZW {

    private int maxDictSize = 65536;   // Number of codewords 2^18 = 4096


    /**
     * Compress given data with Lempel-Ziv-Welch algorithm.
     * Compressed data is stored as 16 bit words into byte array.
     * @param bytes
     * @return compressed bytes
     */
    public byte[] compress(byte[] bytes) {
        HashMap<String, Integer> compressionDictionary = initCompressionDictionary();
        BitList compressed = new BitList();
        int nextCode = 256;

        String w = "" + (bytes[0] < 0 ? bytes[0] + 256 : bytes[0]);

        for (int i = 1; i < bytes.length; i++) {
            int b = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
            String wk = w + b;

            if (compressionDictionary.containsKey(wk)) {
                w = wk;
            } else {
                compressed.add(compressionDictionary.get(w));
                compressionDictionary.put(wk, nextCode);
                nextCode++;
                w = Integer.toString(b);

                // If dictionary is full, reset it
                if (compressionDictionary.size() == maxDictSize - 1) {
                    compressionDictionary = initCompressionDictionary();
                }
            }
        }
        compressed.add(compressionDictionary.get(w));

        return changeBitListTo2ByteArray(compressed);
    }

    /**
     * Decompress given lzw compressed data. Doesn't check if data is valid.
     * @param compressedBytes
     * @return decompressed bytes
     */
    public byte[] decompress(byte[] compressedBytes) {
        BitList decompressed = new BitList();
        BitList compressed = change2ByteArrayToBitList(compressedBytes);
        int[][] decompressionDictionary = new int[maxDictSize][2];

        int nextCode;
        for (nextCode = 0; nextCode < 256; nextCode++) {
            decompressionDictionary[nextCode][0] = -1;          // previous position
            decompressionDictionary[nextCode][1] = nextCode;    // value
        }

        int current = compressed.get(0);
        int[] element = decompressionDictionary[current];
        int[] word = element;
        decompressed.add(element[1]);

        for (int i = 1; i < compressed.size(); i++) {

            current = compressed.get(i);
            element = decompressionDictionary[current];

            // If dictionary doesn't have that word
            if (element[0] == 0) {
                element[0] = compressed.get(i - 1); // Element points to last entry

                // Find first "character" of word
                int previous = word[0];
                if (previous == -1) {
                    element[1] = element[0];
                } else {
                    while(true) {
                        element[1] = decompressionDictionary[previous][1];
                        if (decompressionDictionary[previous][0] == -1) break;
                        previous = decompressionDictionary[previous][0];
                    }
                }
            }

            BitList valuesToAdd = new BitList();

            // Get values
            while(true) {
                int value = decompressionDictionary[current][1];
                valuesToAdd.add(value);
                if (decompressionDictionary[current][0] == -1) break;

                current = decompressionDictionary[current][0];
            }

            // Add values
            for (int j = valuesToAdd.size() - 1; j >= 0; j--) {
                decompressed.add(valuesToAdd.get(j));
            }

            decompressionDictionary[nextCode][0] = compressed.get(i-1);
            decompressionDictionary[nextCode][1] = element[1];
            nextCode++;

            // If dictionary is full, reset it
            if (nextCode == maxDictSize - 1) {
                nextCode = 256;
                for (int j = nextCode; j < maxDictSize; j++) {
                    decompressionDictionary[j][0] = 0;
                    decompressionDictionary[j][1] = 0;
                }
            }

            word = element;
        }

        return changeBitListToByteArray(decompressed);
    }


    private HashMap<String, Integer> initCompressionDictionary() {
        HashMap<String, Integer> compressionDictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            compressionDictionary.put(Integer.toString(i), i);
        }
        return compressionDictionary;
    }


    // items are stored as 16 bits in byte array
    private byte[] changeBitListTo2ByteArray(BitList bitList) {
        int byteArraySize = bitList.size() * 2;
        byte[] byteArray = new byte[byteArraySize];

        int byteCount = 0;

        int leftMask = 65535 - 255;
        int rightMask = 255;

        for (int i = 0; i < bitList.size(); i++) {
            byteArray[byteCount] = (byte) ((bitList.get(i) & leftMask) >> 8);
            byteArray[byteCount + 1] = (byte) ((bitList.get(i) & rightMask));
            byteCount += 2;
        }

        return byteArray;
    }


    private BitList change2ByteArrayToBitList(byte[] bytes) {
        int itemCount = bytes.length / 2;
        BitList bitList = new BitList(itemCount);

        int byteCount = 0;
        for (int i = 0; i < itemCount; i++) {
            int left = bytes[byteCount] < 0 ? bytes[byteCount] + 256 : bytes[byteCount];
            int right = bytes[byteCount + 1] < 0 ? bytes[byteCount + 1] + 256 : bytes[byteCount + 1];
            int value = ((left << 8) + right);
            bitList.add(value);
            byteCount += 2;
        }

        return bitList;
    }

    private byte[] changeBitListToByteArray(BitList bitList) {
        byte[] bytes = new byte[bitList.size()];
        for (int i = 0; i < bitList.size(); i++) {
            bytes[i] = (byte) bitList.get(i);
        }
        return bytes;
    }
}
