package kompakti;

import kompakti.compression.Huffman;
import kompakti.compression.LZW;

/**
 * Program for making LZW and Huffman compressions for any kind of data.
 */
public class Main {
    public static void main(String[] args) {
        String flag = "";

        try {
            flag = args[0];
        } catch (IndexOutOfBoundsException e) {
            help();
            System.exit(1);
        }

        switch (flag) {
            case "--all":
                compression("comp", args);
                break;
            case "--all-decomp":
                decompression("comp", args);
                break;
            case "--lzw":
                compression("lzw", args);
                break;
            case "--lzw-decomp":
                decompression("lzw", args);
                break;
            case "--huffman":
                compression("huffman", args);
                break;
            case "--huffman-decomp":
                decompression("huffman", args);
                break;
            case "--statistics":
                statistics(args);
                break;
            case "--help":
                help();
                break;
            default:
                System.err.println("Invalid argument " + flag);
                help();
                System.exit(1);
        }
    }

    private static void compression(String algorithm, String[] args) {
        byte[] compressed = new byte[1];
        LZW lzw = new LZW();
        Huffman huffman = new Huffman();
        FileReader fr = new FileReader();
        String filename = "";

        try {
            filename = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Give filename as argument");
            System.exit(1);
        }

        byte[] bytes = readFile(filename, fr);

        switch (algorithm) {
            case "comp":
                compressed = huffman.compress(lzw.compress(bytes));
                break;
            case "lzw":
                compressed = lzw.compress(bytes);
                break;
            case "huffman":
                compressed = huffman.compress(bytes);
                break;
        }

        try {
            fr.writeBytes(filename + "." + algorithm, compressed);
            presentBytes(bytes, compressed);
        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

    private static void decompression(String algorithm, String[] args) {
        String filename = "";
        try {
            filename = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Give filename as argument");
            System.exit(1);
        }

        byte[] decompressed = new byte[1];
        LZW lzw = new LZW();
        Huffman huffman = new Huffman();

        FileReader fr = new FileReader();
        byte[] bytes = readFile(filename, fr);

        switch (algorithm) {
            case "comp":
                decompressed = lzw.decompress(huffman.decompress(bytes));
                break;
            case "lzw":
                decompressed = lzw.decompress(bytes);
                break;
            case "huffman":
                decompressed = huffman.decompress(bytes);
                break;
        }

        try {
            fr.writeBytes(filename + ".de" + algorithm, decompressed);
        } catch (Exception e) {
            System.out.println("Can't write to file " + filename);
        }
    }

    private static void statistics(String[] args) {
        System.out.println("Generating statistics...");
        Statistics statistics;

        try {
            if (args.length > 2) {
                statistics = new Statistics(args[1], Integer.parseInt(args[2]));
            } else {
                statistics = new Statistics(args[1]);
            }
            statistics.generate();
            System.out.println();
            System.out.println("Statistics have been generated and saved as statistics.txt");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void help() {
        System.out.println("Usage: [OPTION] [FILE]");
        System.out.println();
        System.out.println("--all: makes LZW + Huffman compression of given file and saves it as .comp file");
        System.out.println("--all-decomp: makes LZW + Huffman decompression of given file and saves it as .decomp file");

        System.out.println("--lzw: makes LZW compression of given file and saves it as .lzw file");
        System.out.println("--lzw-decomp: makes LZW decompression of given file and saves it as .delzw file");

        System.out.println("--huffman: makes Huffman compression of given file and saves it as .huffman file");
        System.out.println("--huffman-decomp: makes Huffman decompression of given file and saves it as .dehuffman file");

        System.out.println("--statistics FOLDER NUM: Uses files in given FOLDER to make stats about huffman and lzw compression.\n  Goes trough tests NUM times (default 10).");
        System.out.println("--help: prints this help message");
    }

    private static void presentBytes(byte[] original, byte[] compressed) {
        System.out.println("Original size: " + original.length);
        System.out.println("Compressed size: " + compressed.length);

        double ratio = (double) original.length / compressed.length;
        System.out.printf("Compress ratio: %.2f\n", ratio);
    }

    private static byte[] readFile(String filename, FileReader fr) {
        byte[] bytes = new byte[1];
        try {
            bytes = fr.readBytes(filename);
            if (bytes.length == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Given filename not readable");
            System.exit(1);
        }
        return bytes;
    }
}
