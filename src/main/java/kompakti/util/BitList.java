package kompakti.util;

public class BitList {
    private int[] bits;
    private int itemCount;
    private int arraySize;

    public BitList() {
        arraySize = 2048;
        bits = new int[arraySize];
        itemCount = 0;
    }

    public BitList(int arraySize) {
        this.arraySize = arraySize;
        bits = new int[arraySize];
        itemCount = 0;
    }

    public int size() {
        return itemCount;
    }

    public void add(int value) {
        if (itemCount == arraySize - 1) {
            increaseSize();
        }
        bits[itemCount] = value;
        itemCount++;
    }

    public int get(int index) {
        if (index < 0 || index >= itemCount) {
            throw new IndexOutOfBoundsException("BitList index out of bounds");
        }
        return bits[index];
    }

//    public void remove()


    // Each item is presented as 2 bytes
    public byte[] getListAsByteArray() {
        int byteArraySize = itemCount * 2;
        byte[] byteArray = new byte[byteArraySize];

        int byteCount = 0;

        int leftMask = 65535 - 255;
        int rightMask = 255;

        for (int i = 0; i < itemCount; i++) {
            byteArray[byteCount] = (byte) ((bits[i] & leftMask) >> 8);
            byteArray[byteCount + 1] = (byte) ((bits[i] & rightMask));
            byteCount += 2;
        }

        return byteArray;
    }


    public int getMaxItem() {
        int max = 0;
        for (int i = 0; i < itemCount; i++) {
            max = max > bits[i] ? max : bits[i];
        }
        return max;
    }

    private void increaseSize() {
        int[] newBits = new int[arraySize + arraySize / 2];
        for (int i = 0; i < arraySize; i++) {
            newBits[i] = bits[i];
        }
        bits = newBits;
        arraySize += arraySize / 2;
    }

    private void decreaseSize() {
        int[] newBits = new int[arraySize / 2];
        for (int i = 0; i < (arraySize / 4); i++) {
            newBits[i] = bits[i];
        }
        bits = newBits;
        arraySize /= 2;
    }

}
