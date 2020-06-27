package kompakti;

import kompakti.compression.Huffman;
import kompakti.compression.LZW;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Statistics {
    File[] files;
    FileReader fr;

    /**
     * Class for generating statistics about LZW, Huffman and both algorithms together.
     * Uses Canterbury corpus test data.
     */
    public Statistics(String dirName) {
        fr = new FileReader();
        try {
            File dir = new File(dirName);
            files = dir.listFiles();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void generate(int n) throws IOException {
        double[][] timeResults = new double[files.length][3];
        long[][] lengthResults = new long[files.length][4];
        LZW lzw = new LZW();
        Huffman huffman = new Huffman();

        if (n < 1) {
            n = 10;
        }

        for (int i = 0; i < files.length; i++) {
            InputStream inputStream = new FileInputStream(files[i]);
            byte[] bytes = inputStream.readAllBytes();
            lengthResults[i][0] = bytes.length;

            long[] times = new long[n];
            long t;

            for (int j = 0; j < n; j++) {
                t = System.nanoTime();
                byte[] lzwCompressed = lzw.compress(bytes);
                t = System.nanoTime() - t;
                lengthResults[i][1] = lzwCompressed.length;
                times[j] = t;
            }
            Arrays.sort(times);
            timeResults[i][0] = times[times.length / 2] / 1000000.0;

            for (int j = 0; j < n; j++) {
                t = System.nanoTime();
                byte[] huffmanCompressed = huffman.compress(bytes);
                t = System.nanoTime() - t;
                lengthResults[i][2] = huffmanCompressed.length;
                times[j] = t;
            }
            Arrays.sort(times);
            timeResults[i][1] = times[times.length / 2] / 1000000.0;

            for (int j = 0; j < n; j++) {
                t = System.nanoTime();
                byte[] lzwCompressed = lzw.compress(bytes);
                byte[] compressed = huffman.compress(lzwCompressed);
                t = System.nanoTime() - t;
                lengthResults[i][3] = compressed.length;
                times[j] = t;
            }
            Arrays.sort(times);
            timeResults[i][2] = times[times.length / 2] / 1000000.0;

            inputStream.close();
        }

        String humanReadable = constructHumanReadableInfo(n, timeResults, lengthResults);

        try {
            fr.writeString("statistics.txt", humanReadable);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String constructHumanReadableInfo(int n, double[][] timeResults, long[][] lengthResults) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Statistics for LZW and Huffman compression algorithms.\n");
        stringBuilder.append("Tests were run " + n + " times.\n\n");

        stringBuilder.append("Compression times (ms):\n");
        stringBuilder.append(String.format("%12s%14s%14s%14s", "", "LZW", "Huffman", "LZW+Huffman"));
        for (int i = 0; i < timeResults.length; i++) {
            stringBuilder.append(String.format("\n%12s", files[i].getName()));
            for (int j = 0; j < timeResults[0].length; j++) {
                stringBuilder.append(String.format("%14.4f", timeResults[i][j]));
            }
        }

        stringBuilder.append("\n\nCompression results (bytes):\n");
        stringBuilder.append(String.format("%12s%14s%14s%14s%14s", "", "Original", "LZW", "Huffman", "LZW+Huffman"));
        for (int i = 0; i < timeResults.length; i++) {
            stringBuilder.append(String.format("\n%12s", files[i].getName()));
            for (int j = 0; j < lengthResults[0].length; j++) {
                stringBuilder.append(String.format("%14d", lengthResults[i][j]));
            }
        }

        stringBuilder.append("\n\nCompression ratio:\n");
        stringBuilder.append(String.format("%12s%14s%14s%14s", "", "LZW", "Huffman", "LZW+Huffman"));
        for (int i = 0; i < timeResults.length; i++) {
            stringBuilder.append(String.format("\n%12s", files[i].getName()));
            for (int j = 1; j < lengthResults[0].length; j++) {
                float ratio = (float) lengthResults[i][0] / lengthResults[i][j];
                stringBuilder.append(String.format("%14.2f", ratio));
            }
        }

        return stringBuilder.toString();
    }
}
