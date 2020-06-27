package kompakti.compression;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class HuffmanTest {
    private Huffman huffman;

    @Before
    public void setUp() {
        huffman = new Huffman();
    }

    @Test
    public void correctCompressionAndDecompressionOfSmallText() {
        String original = "Tama on testi ABABABööABööAB";
        byte[] compressed = huffman.compress(original.getBytes());
        byte[] decompressed = huffman.decompress(compressed);
        assertArrayEquals(original.getBytes(), decompressed);
    }

    @Test
    public void correctCompressionAndDecompressionOfLongText() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("alice29.txt");
        byte[] alice = inputStream.readAllBytes();
        inputStream.close();

        assertEquals(152089, alice.length);
        byte[] compressed = huffman.compress(alice);
        byte[] decompressed = huffman.decompress(compressed);
        assertArrayEquals(alice, decompressed);
    }
}