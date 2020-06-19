package kompakti.util;

public class BitList {
    private byte[] bytes;
    private int byteCount;
    private int bitCount;
    private int arraySize;
    private int bitPosition;

    public BitList() {
        arraySize = 1024;
        bytes = new byte[arraySize];
        byteCount = 0;
        bitCount = 0;
        bitPosition = 0;
    }

    public int size() {
        return byteCount;
    }

    public void add(boolean value) {
        if (value) {
            bytes[byteCount] = (byte) (bytes[byteCount] | (128 >> bitPosition));
        }

        bitCount++;
        bitPosition++;
        if (bitPosition == 8) {
            byteCount++;
            bitPosition = 0;
        }

        if (byteCount == arraySize - 1) {
            increaseSize();
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
        if (bitCount == 0 || index < 0 || index > byteCount) {
            throw new IndexOutOfBoundsException("BitList index out of bounds");
        }

        return bytes[index];
    }

//    public void remove()

    private void increaseSize() {
        byte[] newBytes = new byte[arraySize + arraySize / 2];
        for (int i = 0; i < arraySize; i++) {
            newBytes[i] = bytes[i];
        }
        this.bytes = newBytes;
        this.arraySize += arraySize / 2;
    }

//    private void decreaseSize() {
//        int[] newBits = new int[arraySize / 2];
//        for (int i = 0; i < (arraySize / 4); i++) {
//            newBits[i] = bytes[i];
//        }
//        bytes = newBits;
//        arraySize /= 2;
//    }

}
