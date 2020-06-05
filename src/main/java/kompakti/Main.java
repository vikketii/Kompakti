package kompakti;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String filename = args[0];
//        String filename = "./test-resources/test-1.txt";
        FileReader fr = new FileReader();
        LZW lzw = new LZW();

        try {

            byte[] bytes = fr.readBytes(filename);
            byte[] compressed = lzw.compress(bytes);
            fr.writeFile(filename + ".lzw", compressed);
            presentBytes(bytes, compressed);

//            System.out.println();
//            System.out.println(compressed.length);
//            byte[] wantedResult = new byte[]{4,16,66,16,1,0};
//            System.out.println(wantedResult.length);

        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }


//        try {
//            String originalString = fr.readString(filename);
//            ArrayList<Integer> compressed = lzw.compressString(originalString);
//            fr.writeString(filename + ".lzw", compressed);
//
//            presentString(originalString, compressed);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//            ArrayList<Integer> decompressed = lzw.decompressString(compressed);

    }

    private static void presentBytes(byte[] original, byte[] compressed) {
        System.out.println("Original size: " + original.length);
        System.out.println("Compressed size (with LZW): " + compressed.length);

        double ratio = (double) original.length / compressed.length;
        System.out.printf("Compress ratio: %.2f", ratio);
    }

//    private static void presentString(String original, ArrayList<Integer> compressed) {
//        System.out.println("Original size: " + original.length());
//        System.out.println("Compressed size (with LZW): " + compressed.size());
//
//        double ratio = (double) original.length() / compressed.size();
//        System.out.printf("Compress ratio: %.2f", ratio);
//    }

}
