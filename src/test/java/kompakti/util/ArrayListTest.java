package kompakti.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ArrayListTest {
    private ArrayList arrayList;

    @Before
    public void setUp() {
        arrayList = new ArrayList();
    }

    @Test
    public void correctSizeIsCreated() {
        ArrayList arrayListWithSize = new ArrayList(2048);
        assertEquals(0, arrayList.size());
        assertEquals(0, arrayListWithSize.size());
    }

    @Test
    public void oneItemCorrectlyAdded() {
        arrayList.add(10);
        assertEquals(10, arrayList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void negativeIndexThrowsException() {
        arrayList.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void notExistingIndexThrowsException() {
        arrayList.get(0);
    }

    @Test
    public void listSizeCorrectlyIncreased() {
        for (int i = 0; i < 2049; i++) {
            arrayList.add(i);
        }
        assertEquals(2048, arrayList.get(2048));
    }


}