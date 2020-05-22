package kompakti;

import java.util.ArrayList;
import java.util.HashMap;

public class LZW {
    public byte[] compress(byte[] bytes) {
        HashMap<Byte, Integer> compressionGuide = new HashMap<>();
        ArrayList<Byte> compressed = new ArrayList<Byte>();

//        for (int i = 8; i < bytes.length; i++) {
//            if (i+1 < bytes.length) {
//                compressed.contains(byte[])
//            }
//        }

        byte[] compressedBytes = bytes;
        return compressedBytes;
    }

    public ArrayList<Integer> compressString(String input) {
        int guideInitSize = 256;
        HashMap<String, Integer> compressionGuide = new HashMap<>();
        for (int i = 0; i < guideInitSize; i++) {
            compressionGuide.put(Character.toString((char) i), i);
        }

        String w = "";
        ArrayList<Integer> compressed = new ArrayList<>();

        for (char k : input.toCharArray()) {
            String wk = w + k;
            if (compressionGuide.containsKey(wk)) {
                w = wk;
            } else {
                compressed.add(compressionGuide.get(w));
                compressionGuide.put(wk, guideInitSize);
                w = Character.toString(k);
            }
        }


        return compressed;
    }
}
