package kompakti.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterTest {
    private Converter converter;

    @Before
    public void setUp() { converter = new Converter(); }

    @Test
    public void changeBitListToByteArrayIsCorrect() {
        BitList bitListToConvert = new BitList();
        bitListToConvert.add(0);
        bitListToConvert.add(10);
        bitListToConvert.add(127);
        bitListToConvert.add(128);
        bitListToConvert.add(255);
        byte[] result = converter.changeBitListToByteArray(bitListToConvert);
        assertArrayEquals(new byte[]{0, 10, 127, -128, -1}, result);
    }

    @Test
    public void changeByteArrayToBitListIsCorrect() {
        byte[] bytesToConvert = new byte[] {0, 10, 127, -128, -1};
        BitList result = converter.changeByteArrayToBitList(bytesToConvert);

        BitList correct = new BitList();
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
    public void changeBitListTo2ByteArrayIsCorrect() {
        BitList bitListToConvert = new BitList();
        bitListToConvert.add(0);
        bitListToConvert.add(127);
        bitListToConvert.add(128);
        bitListToConvert.add(255);
        bitListToConvert.add(256);
        bitListToConvert.add(32767);
        bitListToConvert.add(32768); // 2^15
        bitListToConvert.add(65535);
        byte[] result = converter.changeBitListTo2ByteArray(bitListToConvert);
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
    public void change2ByteArrayToBitListIsCorrect() {
        byte[] bytesToConvert = {0, 0, 0, 127, 0, -128, 0, -1, 1, 0, 127, -1, -128, 0, -1, -1};
        BitList result = converter.change2ByteArrayToBitList(bytesToConvert);

        BitList correct = new BitList();
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

}