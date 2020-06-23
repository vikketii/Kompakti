package kompakti.compression;

import kompakti.compression.LZW;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LZWTest {
    private LZW lzw;

    @Before
    public void setUp() {
        lzw = new LZW();
    }

    @Test
    public void correctCompressionOfShortString1() {
        String testString = "ABABAB";
        byte[] compressionResult = lzw.compress(testString.getBytes());
        byte[] correctResult = new byte[]{0,65,0,66,1,0,1,0};
        assertArrayEquals(correctResult, compressionResult);
    }

    @Test
    public void correctCompressionOfShortString2() {
        String testString = "AAABAABBBB";
        byte[] compressionResult = lzw.compress(testString.getBytes());
        byte[] correctResult = new byte[]{0,65,1,0,0,66,1,1,0,66,1,4};
        assertArrayEquals(correctResult, compressionResult);
    }

    @Test
    public void correctCompressionOfShortString3() {
        String testString = "ACBBAAC";
        byte[] compressionResult = lzw.compress(testString.getBytes());
        byte[] correctResult = new byte[]{0,65,0,67,0,66,0,66,0,65,1,0};
        assertArrayEquals(correctResult, compressionResult);
    }

    @Test
    public void correctDecompressionOfShortString1() {
        String correct = "ABABAB";
        byte[] decompressionResult = lzw.decompress(new byte[]{0,65,0,66,1,0,1,0});
        assertArrayEquals(decompressionResult, correct.getBytes());
    }

    @Test
    public void correctDecompressionOfShortString2() {
        String correct = "AAABAABBBB";
        byte[] decompressionResult = lzw.decompress(new byte[]{0,65,1,0,0,66,1,1,0,66,1,4});
        assertArrayEquals(decompressionResult, correct.getBytes());
    }

    @Test
    public void correctDecompressionOfShortString3() {
        String correct = "ACBBAAC";
        byte[] decompressionResult = lzw.decompress(new byte[]{0,65,0,67,0,66,0,66,0,65,1,0});
        assertArrayEquals(decompressionResult, correct.getBytes());
    }
}