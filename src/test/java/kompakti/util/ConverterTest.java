package kompakti.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterTest {
    private Converter converter;

    @Before
    public void setUp() { converter = new Converter(); }

    @Test
    public void changeArrayListToByteArrayIsCorrect() {
        ArrayList arrayListToConvert = new ArrayList();
        arrayListToConvert.add(0);
        arrayListToConvert.add(10);
        arrayListToConvert.add(127);
        arrayListToConvert.add(128);
        arrayListToConvert.add(255);
        byte[] result = converter.changeArrayListToByteArray(arrayListToConvert);
        assertArrayEquals(new byte[]{0, 10, 127, -128, -1}, result);
    }

    @Test
    public void changeByteArrayToArrayListIsCorrect() {
        byte[] bytesToConvert = new byte[] {0, 10, 127, -128, -1};
        ArrayList result = converter.changeByteArrayToArrayList(bytesToConvert);

        ArrayList correct = new ArrayList();
        correct.add(0);
        correct.add(10);
        correct.add(127);
        correct.add(128);
        correct.add(255);

        assertEquals(correct.size(), result.size());
        for (int i = 0; i < correct.size(); i++) {
            assertEquals(correct.get(i), result.get(i));
        }
    }

    @Test
    public void changeArrayListTo2ByteArrayIsCorrect() {
        ArrayList arrayListToConvert = new ArrayList();
        arrayListToConvert.add(0);
        arrayListToConvert.add(127);
        arrayListToConvert.add(128);
        arrayListToConvert.add(255);
        arrayListToConvert.add(256);
        arrayListToConvert.add(32767);
        arrayListToConvert.add(32768); // 2^15
        arrayListToConvert.add(65535);
        byte[] result = converter.changeArrayListTo2ByteArray(arrayListToConvert);
        byte[] correct = {
                0, 0,
                0, 127,
                0, -128,
                0, -1,
                1, 0,
                127, -1,
                -128, 0,
                -1, -1

        };
        assertArrayEquals(correct, result);
    }

    @Test
    public void change2ByteArrayToArrayListIsCorrect() {
        byte[] bytesToConvert = {0, 0, 0, 127, 0, -128, 0, -1, 1, 0, 127, -1, -128, 0, -1, -1};
        ArrayList result = converter.change2ByteArrayToArrayList(bytesToConvert);

        ArrayList correct = new ArrayList();
        correct.add(0);
        correct.add(127);
        correct.add(128);
        correct.add(255);
        correct.add(256);
        correct.add(32767);
        correct.add(32768); // 2^15
        correct.add(65535);

        assertEquals(correct.size(), result.size());
        for (int i = 0; i < correct.size(); i++) {
            assertEquals(correct.get(i), result.get(i));
        }
    }

    @Test
    public void changeByteToUnsignedIntIsCorrect() {
        assertEquals(0, converter.byteToUnsignedInt((byte) 0));
        assertEquals(127, converter.byteToUnsignedInt((byte) 127));
        assertEquals(128, converter.byteToUnsignedInt((byte) 128));
        assertEquals(255, converter.byteToUnsignedInt((byte) 255));
        assertEquals(0, converter.byteToUnsignedInt((byte) 256));
    }

    @Test
    public void addOneToBitStringIsCorrect() {
        String bitString = "0";
        bitString = converter.addOneToBitString(bitString);
        assertEquals("1", bitString);

        bitString = "010";
        bitString = converter.addOneToBitString(bitString);
        assertEquals("011", bitString);
//        bitString = converter.addOneToBitString(bitString);
//        assertEquals("100", bitString);
    }

}