package kompakti;

import kompakti.compression.Huffman;
import kompakti.compression.LZW;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Statistics about LZW, Huffman and both algorithms together.
 */
public class Statistics {
    private File[] files;
    private FileReader fr;
    private int n;

    /**
     * Finds all files in given directory for testing.
     *
     * @param dirName Directory name.
     */
    public Statistics(String dirName) {
        fr = new FileReader();
        try {
            File dir = new File(dirName);
            files = dir.listFiles();
        } catch (Exception e) {
            System.out.println(e);
        }
        this.n = 10;
    }


    /**
     * Finds all files in given directory for testing.
     *
     * @param dirName Directory name.
     * @param n Number of times tests are run.
     */
    public Statistics(String dirName, int n) {
        fr = new FileReader();
        try {
            File dir = new File(dirName);
            files = dir.listFiles();
        } catch (Exception e) {
            System.out.println(e);
        }
        this.n = n;
    }

    /**
     * Generate statics out of found files with lzw, huffman and both together.
     * Prints results and writes them in statistics.txt for later inspection.
     *
     * @throws IOException
     */
    public void generate() throws IOException {
        double[][] compressionTimeResults = new double[files.length][3];
        double[][] decompressionTimeResults = new double[files.length][3];
        long[][] compressionLengthResults = new long[files.length][4];


        for (int i = 0; i < files.length; i++) {
            InputStream inputStream = new FileInputStream(files[i]);
            byte[] bytes = inputStream.readAllBytes();
            makeTests(i, bytes, compressionTimeResults, decompressionTimeResults, compressionLengthResults);
            inputStream.close();
        }

        String results = constructResultsString(n, compressionTimeResults, decompressionTimeResults, compressionLengthResults);
        System.out.println(results);

        try {
            fr.writeString("statistics.txt", results);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String constructResultsString(int n, double[][] compressionTimeResults, double[][] decompressionTimeResults, long[][] compressionLengthResults) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Statistics for LZW and Huffman compression algorithms.\n");
        stringBuilder.append("Tests were run " + n + " times.\n\n");

        stringBuilder.append("Compression times (ms):\n");
        stringBuilder.append(String.format("%12s%14s%14s%14s", "", "LZW", "Huffman", "LZW+Huffman"));
        for (int i = 0; i < compressionTimeResults.length; i++) {
            stringBuilder.append(String.format("\n%12s", files[i].getName()));
            for (int j = 0; j < compressionTimeResults[0].length; j++) {
                stringBuilder.append(String.format("%14.4f", compressionTimeResults[i][j]));
            }
        }

        stringBuilder.append("\n\nDecompression times (ms):\n");
        stringBuilder.append(String.format("%12s%14s%14s%14s", "", "LZW", "Huffman", "LZW+Huffman"));
        for (int i = 0; i < decompressionTimeResults.length; i++) {
            stringBuilder.append(String.format("\n%12s", files[i].getName()));
            for (int j = 0; j < decompressionTimeResults[0].length; j++) {
                stringBuilder.append(String.format("%14.4f", decompressionTimeResults[i][j]));
            }
        }

        stringBuilder.append("\n\nCompression results (bytes):\n");
        stringBuilder.append(String.format("%12s%14s%14s%14s%14s", "", "Original", "LZW", "Huffman", "LZW+Huffman"));
        for (int i = 0; i < compressionTimeResults.length; i++) {
            stringBuilder.append(String.format("\n%12s", files[i].getName()));
            for (int j = 0; j < compressionLengthResults[0].length; j++) {
                stringBuilder.append(String.format("%14d", compressionLengthResults[i][j]));
            }
        }

        stringBuilder.append("\n\nCompression ratio:\n");
        stringBuilder.append(String.format("%12s%14s%14s%14s", "", "LZW", "Huffman", "LZW+Huffman"));
        for (int i = 0; i < compressionTimeResults.length; i++) {
            stringBuilder.append(String.format("\n%12s", files[i].getName()));
            for (int j = 1; j < compressionLengthResults[0].length; j++) {
                float ratio = (float) compressionLengthResults[i][0] / compressionLengthResults[i][j];
                stringBuilder.append(String.format("%14.2f", ratio));
            }
        }

        return stringBuilder.toString();
    }

    private void makeTests(int i, byte[] bytes, double[][] compressionTimeResults, double[][] decompressionTimeResults, long[][] compressionLengthResults) {
        LZW lzw = new LZW();
        Huffman huffman = new Huffman();
        compressionLengthResults[i][0] = bytes.length;
        long[] compressionTimes = new long[n];
        long[] decompressionTimes = new long[n];
        long t1;
        long t2;

        for (int j = 0; j < n; j++) {
            t1 = System.nanoTime();
            byte[] lzwCompressed = lzw.compress(bytes);
            t1 = System.nanoTime() - t1;
            t2 = System.nanoTime();
            lzw.decompress(lzwCompressed);
            t2 = System.nanoTime() - t2;
            compressionLengthResults[i][1] = lzwCompressed.length;
            compressionTimes[j] = t1;
            decompressionTimes[j] = t2;
        }
        Arrays.sort(compressionTimes);
        compressionTimeResults[i][0] = compressionTimes[compressionTimes.length / 2] / 1000000.0;
        Arrays.sort(decompressionTimes);
        decompressionTimeResults[i][0] = decompressionTimes[decompressionTimes.length / 2] / 1000000.0;

        for (int j = 0; j < n; j++) {
            t1 = System.nanoTime();
            byte[] huffmanCompressed = huffman.compress(bytes);
            t1 = System.nanoTime() - t1;
            t2 = System.nanoTime();
            huffman.decompress(huffmanCompressed);
            t2 = System.nanoTime() - t2;
            compressionLengthResults[i][2] = huffmanCompressed.length;
            compressionTimes[j] = t1;
            decompressionTimes[j] = t2;
        }
        Arrays.sort(compressionTimes);
        compressionTimeResults[i][1] = compressionTimes[compressionTimes.length / 2] / 1000000.0;
        Arrays.sort(decompressionTimes);
        decompressionTimeResults[i][1] = decompressionTimes[decompressionTimes.length / 2] / 1000000.0;

        for (int j = 0; j < n; j++) {
            t1 = System.nanoTime();
            byte[] compressed = huffman.compress(lzw.compress(bytes));
            t1 = System.nanoTime() - t1;
            t2 = System.nanoTime();
            lzw.decompress(huffman.decompress(compressed));
            t2 = System.nanoTime() - t2;
            compressionLengthResults[i][3] = compressed.length;
            compressionTimes[j] = t1;
            decompressionTimes[j] = t2;
        }
        Arrays.sort(compressionTimes);
        compressionTimeResults[i][2] = compressionTimes[compressionTimes.length / 2] / 1000000.0;
        Arrays.sort(decompressionTimes);
        decompressionTimeResults[i][2] = decompressionTimes[decompressionTimes.length / 2] / 1000000.0;
    }
}
