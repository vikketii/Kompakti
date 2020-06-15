package kompakti;

import kompakti.compression.Huffman;
import kompakti.compression.LZW;

public class Main {
    public static void main(String[] args) {
        String flag = "";
        String filename = "";

        try {
            flag = args[0];
            filename = args[1];
        } catch (Exception e) {
            System.err.println("Use -h to get help how to use this program");
            System.exit(1);
        }

        FileReader fr = new FileReader();
        byte[] bytes = new byte[0];
        try {
            bytes = fr.readBytes(filename);
        } catch (Exception e) {
            System.err.println("Given filename not readable");
            System.exit(1);
        }

        switch (flag) {
            case "--lzw":
                LZWCompression(bytes, fr, filename);
                break;
            case "--huffman":
                HuffmanCompression(bytes, fr, filename);
                break;
            case "-h":
                help();
                break;
            default:
                System.err.println("Invalid argument " + flag);
                System.exit(1);
        }
    }

    private static void LZWCompression(byte[] bytes, FileReader fr, String filename) {
        LZW lzw = new LZW();
        byte[] compressed = lzw.compress(bytes);
        byte[] decompressed = lzw.decompress(compressed);

        try {
            fr.writeFile(filename + ".lzw", compressed);
            presentBytes(bytes, compressed);
            fr.writeFile(filename + ".lzwdecomp", decompressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void HuffmanCompression(byte[] bytes, FileReader fr, String filename) {
        Huffman huff = new Huffman();
        byte[] compressed = huff.compress(bytes);
//        byte[] decompressed = huff.decompress(compressed);

        try {
            fr.writeFile(filename + ".huff", compressed);
            presentBytes(bytes, compressed);
//            fr.writeFile(filename + ".huffdecomp", decompressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }


    private static void help() {
        System.out.println("Usage: [OPTION] [FILE]");
        System.out.println();
        System.out.println("--lzw: makes LZW compression of given file and saves it as .lzw file");
        System.out.println("--help: prints this help message");
    }


    private static void presentBytes(byte[] original, byte[] compressed) {
        System.out.println("Original size: " + original.length);
        System.out.println("Compressed size: " + compressed.length);

        double ratio = (double) original.length / compressed.length;
        System.out.printf("Compress ratio: %.2f\n", ratio);
    }
}
