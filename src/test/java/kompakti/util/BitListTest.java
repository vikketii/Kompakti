package kompakti.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BitListTest {
    private BitList bitList;

    @Before
    public void setUp() {
        bitList = new BitList();
    }

    @Test
    public void oneBitIsAddedCorrectly() {
        bitList.addBit(true);
        assertTrue(bitList.getBit(0));
    }

    @Test
    public void oneByteIsAddedCorrectly1() {
        bitList.addByte((byte) 85);
        assertEquals(85, bitList.getByte(0));
    }

    @Test
    public void oneByteIsAddedCorrectly2() {
        bitList.addByte((byte) -128);
        assertEquals(-128, bitList.getByte(0));
    }

    @Test
    public void sizeIsCorrect() {
        assertEquals(0, bitList.size());

        bitList.addBit(true);
        assertEquals(1, bitList.size());

        bitList.addByte((byte) 2);
        assertEquals(9, bitList.size());
//        assertEquals(2, bitList.byteSize());
    }

    @Test
    public void byteSizeIsCorrect() {
        assertEquals(0, bitList.byteSize());

        bitList.addBit(true);
        assertEquals(1, bitList.byteSize());
//
        bitList.addByte((byte) 2);
        assertEquals(2, bitList.byteSize());
    }

    @Test
    public void sizeIncreaseIsCorrect() {
        for (int i = 0; i < 1025 ; i++) {
            bitList.addByte((byte) 127);
        }
        assertEquals(127, bitList.getByte(1024));
        assertEquals(1025, bitList.byteSize());
    }
}