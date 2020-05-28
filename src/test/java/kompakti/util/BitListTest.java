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
    public void correctSizeIsCreated() {
        BitList bitListWithSize = new BitList(2048);
        assertEquals(0, bitList.size());
        assertEquals(0, bitListWithSize.size());
    }

    @Test
    public void oneItemCorrectlyAdded() {
        bitList.add(10);
        assertEquals(10, bitList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void negativeIndexThrowsException() {
        bitList.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void notExistingIndexThrowsException() {
        bitList.get(0);
    }

    @Test
    public void listSizeCorrectlyIncreased() {
        for (int i = 0; i < 2049; i++) {
            bitList.add(i);
        }
        assertEquals(2048, bitList.get(2048));
    }


}