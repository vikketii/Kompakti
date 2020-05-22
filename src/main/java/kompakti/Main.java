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

            byte[] bytes = fr.readBytes(filename);
            byte[] compressed = lzw.compress(bytes);

            present(bytes, compressed);

        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void present(byte[] original, byte[] compressed) {
        System.out.println("Original size: " + original.length);
        System.out.println("Compressed size (with LZW): " + compressed.length);
        System.out.println("Compress ratio: " + (compressed.length / original.length));
    }

}
