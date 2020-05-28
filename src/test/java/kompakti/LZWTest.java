package kompakti;

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
    public void correctOutputWithShortString() {
        String testString = "ABABAB.";
        byte[] compressionResult = lzw.compress(testString.getBytes());
        byte[] wantedResult = new byte[]{4,16,66,16,1,0};

        assertArrayEquals(wantedResult, compressionResult);
    }
}