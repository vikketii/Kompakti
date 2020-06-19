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

    public BitList(byte[] bytes) {

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
