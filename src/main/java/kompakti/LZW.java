package kompakti;

import kompakti.util.BitList;
import kompakti.util.HashMap;

public class LZW {

//    private int maxDictSize = 4096;   // Number of codewords 2^12 = 4096
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

        byte[] compressedBytes = changeBitListTo2ByteArray(compressed);

        return compressedBytes;
    }

    public byte[] decompress(byte[] compressedBytes) {
        byte[] bytes = new byte[1];

        BitList compressed = change2ByteArrayToBitList(compressedBytes);
        HashMap<String, Integer> compressionDictionary = initCompressionDictionary();

        for (int i = 0; i < compressed.size(); i++) {
            int b = compressed.get(i);

        }

        return bytes;
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
            int value = (bytes[byteCount] << 8) + bytes[byteCount+1];
            bitList.add(value);
            byteCount += 2;
        }

        return bitList;
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
