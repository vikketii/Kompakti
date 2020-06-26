package kompakti;

import kompakti.compression.Huffman;
import kompakti.compression.LZW;

public class Main {
    public static void main(String[] args) {
        String flag = "";
        String filename = "";

        if (args[0].equals("--statistic")) {
            statistics();
            System.exit(0);
        }

        try {
            flag = args[0];
            filename = args[1];
        } catch (Exception e) {
            help();
            System.exit(0);
        }

        FileReader fr = new FileReader();
        byte[] bytes = new byte[0];
        try {
            bytes = fr.readBytes(filename);
            if (bytes.length == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Given filename not readable");
            System.exit(1);
        }

        switch (flag) {
            case "--normal":
                compression(bytes, fr, filename);
                break;
            case "--lzw":
                lzwCompression(bytes, fr, filename);
                break;
            case "--lzw-decomp":
                lzwDecompression(bytes, fr, filename);
                break;
            case "--huffman":
                huffmanCompression(bytes, fr, filename);
                break;
            case "--huffman-decomp":
                huffmanDecompression(bytes, fr, filename);
                break;
            case "--statistic":
                statistics();
                break;
            default:
                System.err.println("Invalid argument " + flag);
                help();
                System.exit(1);
        }
    }

    private static void compression(byte[] bytes, FileReader fr, String filename) {
        LZW lzw = new LZW();
        Huffman huffman = new Huffman();

        byte[] lzwCompressed = lzw.compress(bytes);
        byte[] huffmanCompressed = huffman.compress(lzwCompressed);
        byte[] huffmanDecompressed = huffman.decompress(huffmanCompressed);
        byte[] lzwDecompressed = lzw.decompress(huffmanDecompressed);

        try {
            fr.writeFile(filename + ".comp", huffmanCompressed);
            fr.writeFile(filename + ".compdecomp", lzwDecompressed);
            presentBytes(bytes, lzwCompressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void lzwCompression(byte[] bytes, FileReader fr, String filename) {
        LZW lzw = new LZW();
        byte[] compressed = lzw.compress(bytes);
//        byte[] decompressed = lzw.decompress(compressed);

        try {
            fr.writeFile(filename + ".lzw", compressed);
//            fr.writeFile(filename + ".lzwdecomp", decompressed);
            presentBytes(bytes, compressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void lzwDecompression(byte[] bytes, FileReader fr, String filename) {
        LZW lzw = new LZW();
        byte[] decompressed = lzw.decompress(bytes);

        try {
            fr.writeFile(filename + ".lzwdecomp", decompressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void huffmanCompression(byte[] bytes, FileReader fr, String filename) {
        Huffman huff = new Huffman();
        byte[] compressed = huff.compress(bytes);
//        byte[] decompressed = huff.decompress(compressed);

        try {
            fr.writeFile(filename + ".huff", compressed);
//            fr.writeFile(filename + ".huffdecomp", decompressed);
            presentBytes(bytes, compressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void huffmanDecompression(byte[] bytes, FileReader fr, String filename) {
        Huffman huff = new Huffman();
        byte[] decompressed = huff.decompress(bytes);

        try {
            fr.writeFile(filename + ".huffdecomp", decompressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void statistics() {
        System.out.println("Generating statistics...");

//        try {
            Statistics statistics = new Statistics();
            statistics.generate();
            System.out.println("Statistics have been generated.");
            System.out.println("Human readable file is named statistics.txt");
            System.out.println("Csv data is named statistics.csv");
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
    }


    private static void help() {
        System.out.println("Usage: [OPTION] [FILE]");
        System.out.println();
        System.out.println("--normal: <TODO> makes LZW + Huffman compression of given file and saves it as .comp file");
        System.out.println("--lzw: makes LZW compression of given file and saves it as .lzw file");
        System.out.println("--lzw-decomp: makes LZW decompression of given file and saves it as .lzwdecomp file");
        System.out.println("--huffman: makes Huffman compression of given file and saves it as .huff file");
        System.out.println("--huffman-decomp: makes Huffman decompression of given file and saves it as .huffdecomp file");
        System.out.println("--statistics: <TODO> uses 'Canterbury corpus' files to make stats about huffman and lzw compression");
        System.out.println("--help: prints this help message");
    }


    private static void presentBytes(byte[] original, byte[] compressed) {
        System.out.println("Original size: " + original.length);
        System.out.println("Compressed size: " + compressed.length);

        double ratio = (double) original.length / compressed.length;
        System.out.printf("Compress ratio: %.2f\n", ratio);
    }
}
