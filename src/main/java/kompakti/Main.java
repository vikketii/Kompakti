package kompakti;

import java.util.ArrayList;
import kompakti.FileReader;
import kompakti.LZW;

public class Main {
    public static void main(String[] args) {
        try {
            String filename = args[0];

            FileReader fr = new FileReader();
            LZW lzw = new LZW();

//            byte[] bytes = fr.readBytes(filename);
//            byte[] compressed = lzw.compress(bytes);

            String originalString = fr.readString(filename);
            ArrayList<Integer> compressed = lzw.compressString(originalString);

//            presentBytes(bytes, compressed);
            presentString(originalString, compressed);

        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void presentBytes(byte[] original, byte[] compressed) {
        System.out.println("Original size: " + original.length);
        System.out.println("Compressed size (with LZW): " + compressed.length);
        System.out.println("Compress ratio: " + (compressed.length / original.length));
    }

    private static void presentString(String original, ArrayList<Integer> compressed) {
        System.out.println("Original size: " + original.length());
        System.out.println("Compressed size (with LZW): " + compressed.size());
        System.out.println("Compress ratio: " + (compressed.size() / original.length()));
    }

}
