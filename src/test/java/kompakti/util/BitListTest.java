package kompakti.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BitListTest {
    private BitList bitList;

    @Before
    public void setUp() throws Exception {
        bitList = new BitList();
    }

    @Test
    public void oneBitIsAddedCorrectly() {
        bitList.add(true);
        assertTrue(bitList.getBit(0));
    }

    @Test
    public void oneByteIsAddedCorrectly() {
        bitList.add(false);
        bitList.add(true);
        bitList.add(false);
        bitList.add(true);

        bitList.add(false);
        bitList.add(true);
        bitList.add(false);
        bitList.add(true);
        assertEquals(85, bitList.getByte(0));
    }
}