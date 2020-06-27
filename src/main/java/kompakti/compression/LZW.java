package kompakti.compression;

import kompakti.util.ArrayList;
import kompakti.util.Converter;
import kompakti.util.HashMap;

public class LZW {

    private int maxDictSize = 65536;   // Number of codewords 2^16 = 65536
    private Converter converter = new Converter();

    /**
     * Compress given data with Lempel-Ziv-Welch algorithm.
     * Compressed data is stored as 16 bit words into byte array.
     * @param bytes
     * @return compressed bytes
     */
    public byte[] compress(byte[] bytes) {
        HashMap<String, Integer> compressionDictionary = initCompressionDictionary();
        ArrayList toCompress = converter.changeByteArrayToArrayList(bytes);
        ArrayList compressed = new ArrayList();
        int nextCode = 256;

        String w = "" + (char) toCompress.get(0);

        for (int i = 1; i < toCompress.size(); i++) {
            String b = "" + (char) toCompress.get(i);
            String wk = w + b;

            if (compressionDictionary.containsKey(wk)) {
                w = wk;
            } else {
                compressed.add(compressionDictionary.get(w));
                compressionDictionary.put(wk, nextCode);
                nextCode++;
                w = b;

                // If dictionary is full, reset it
                if (compressionDictionary.size() >= maxDictSize - 1) {
                    compressionDictionary = initCompressionDictionary();
                    nextCode = 256;
                }
            }
        }
        compressed.add(compressionDictionary.get(w));

        return converter.changeArrayListTo2ByteArray(compressed);
    }

    /**
     * Decompress given lzw compressed data. Doesn't check if data is valid.
     * @param compressedBytes
     * @return decompressed bytes
     */
    public byte[] decompress(byte[] compressedBytes) {
        ArrayList decompressed = new ArrayList();
        ArrayList compressed = converter.change2ByteArrayToArrayList(compressedBytes);
        int[][] decompressionDictionary = new int[maxDictSize][2]; // [0] previous position, [1] value

        int nextCode;
        for (nextCode = 0; nextCode < 256; nextCode++) {
            decompressionDictionary[nextCode][0] = -1;
            decompressionDictionary[nextCode][1] = nextCode;
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
                element[0] = compressed.get(i - 1); // Point element to last entry

                // Find first "character" of word
                int previous = word[0];
                if (previous == -1) {
                    element[1] = element[0];
                } else {
                    while (true) {
                        element[1] = decompressionDictionary[previous][1];
                        if (decompressionDictionary[previous][0] == -1) {
                            break;
                        }
                        previous = decompressionDictionary[previous][0];
                    }
                }
            }

            ArrayList valuesToAdd = new ArrayList();

            // Get values
            while (true) {
                int value = decompressionDictionary[current][1];
                valuesToAdd.add(value);
                if (decompressionDictionary[current][0] == -1) {
                    break;
                }

                current = decompressionDictionary[current][0];
            }

            // Add values
            for (int j = valuesToAdd.size() - 1; j >= 0; j--) {
                decompressed.add(valuesToAdd.get(j));
            }

            decompressionDictionary[nextCode][0] = compressed.get(i - 1);
            decompressionDictionary[nextCode][1] = valuesToAdd.get(valuesToAdd.size() - 1);
            nextCode++;
            word = element;

            // If dictionary is full, reset it
            if (nextCode >= maxDictSize - 1) {
                word[0] = -1;
                nextCode = 256;
                for (int j = nextCode; j < maxDictSize; j++) {
                    decompressionDictionary[j][0] = 0;
                    decompressionDictionary[j][1] = 0;
                }
            }
        }

        return converter.changeArrayListToByteArray(decompressed);
    }

    private HashMap<String, Integer> initCompressionDictionary() {
        HashMap<String, Integer> compressionDictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            compressionDictionary.put("" + (char) i, i);
        }
        return compressionDictionary;
    }
}
