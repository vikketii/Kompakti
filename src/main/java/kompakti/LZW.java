package kompakti;

import kompakti.util.BitList;

import java.util.HashMap;

public class LZW {

    private int maxDictSize = 4096;   // Number of codewords 2^12 = 4096


    /**
     * Compress given data with Lempel-Ziv-Welch algorithm.
     * Compressed data is stored as 12 bit words into byte array.
     * @param bytes
     * @return compressed bytes
     */
    public byte[] compress(byte[] bytes) {
        int nextCode;

        HashMap<String, Integer> compressionDictionary = new HashMap<>();
        for (nextCode = 0; nextCode < 256; nextCode++) {
            compressionDictionary.put(Integer.toString(nextCode), nextCode);
        }

        String w = "" + (bytes[0] < 0 ? bytes[0] + 256 : bytes[0]);
        BitList compressed = new BitList();

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
                    compressionDictionary = new HashMap<>();
                    for (nextCode = 0; nextCode < 256; nextCode++) {
                        compressionDictionary.put(Integer.toString(nextCode), nextCode);
                    }
                }
            }
        }

        byte[] compressedBytes = compressed.getListAsByteArray();

        return compressedBytes;
    }

//    public ArrayList<Integer> compressString(String input) {
//        int nextCode = 0;
//        HashMap<String, Integer> compressionGuide = new HashMap<>();
//        for (nextCode = 0; nextCode < 256; nextCode++) {
//            compressionGuide.put(Character.toString((char) nextCode), nextCode);
//        }
//
//        String w = "";
//        System.out.println(input.charAt(2));
//        ArrayList<Integer> compressed = new ArrayList<>();
//
//        for (char k : input.toCharArray()) {
//            String wk = w + k;
//            if (compressionGuide.containsKey(wk)) {
//                w = wk;
//            } else if (compressionGuide.size() < MAX_DICT_SIZE) {
//                compressed.add(compressionGuide.get(w));
//                compressionGuide.put(wk, nextCode);
//                nextCode++;
//                w = Character.toString(k);
//            }
//        }
//
//        return compressed;
//    }
}
