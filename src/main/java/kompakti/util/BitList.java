package kompakti.util;

public class BitList {
    private byte[] bytes;
    private int byteCount;
    private int bitCount;
    private int arraySize;
    private int bitPosition;

    public BitList() {
        arraySize = 2048;
        bytes = new byte[arraySize];
        byteCount = 0;
        bitCount = 0;
        bitPosition = 0;
    }

    public int size() {
        return bitCount;
    }

    public int byteSize() {
        return byteCount;
    }

    public void addBit(boolean value) {
        if (bitPosition == 0) {
            byteCount++;
        }

        if (value) {
            bytes[bitCount / 8] |= (128 >> bitPosition);
        }

        bitCount++;
        bitPosition++;
        if (bitPosition == 8) {
            bitPosition = 0;
        }
        if (byteCount == arraySize - 1) {
            increaseSize();
        }
    }

    public void addByte(byte value) {
        for (int i = 0; i < 8; i++) {
            addBit((value & (128 >> i)) != 0);
        }
    }

    public boolean getBit(int index) {
        if (index < 0 || index >= bitCount) {
            throw new IndexOutOfBoundsException("BitList index out of bounds");
        }
        int result = (bytes[index / 8] & (byte) (128 >> (index % 8)));
        return result != 0;
    }

    public byte getByte(int index) {
        if (bitCount == 0 || index < 0 || index >= byteCount) {
            throw new IndexOutOfBoundsException("BitList index out of bounds");
        }

        return bytes[index];
    }

    private void increaseSize() {
        byte[] newBytes = new byte[arraySize * 2];
        for (int i = 0; i < arraySize; i++) {
            newBytes[i] = bytes[i];
        }
        this.bytes = newBytes;
        this.arraySize *= 2;
    }
}
