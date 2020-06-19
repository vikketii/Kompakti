package kompakti.util;

public class ArrayList {
    private int[] items;
    private int itemCount;
    private int arraySize;

    public ArrayList() {
        arraySize = 2048;
        items = new int[arraySize];
        itemCount = 0;
    }

    public ArrayList(int arraySize) {
        this.arraySize = arraySize;
        items = new int[arraySize];
        itemCount = 0;
    }

    public int size() {
        return itemCount;
    }

    public void add(int value) {
        if (itemCount == arraySize - 1) {
            increaseSize();
        }
        items[itemCount] = value;
        itemCount++;
    }

    public int get(int index) {
        if (index < 0 || index >= itemCount) {
            throw new IndexOutOfBoundsException("BitList index out of bounds");
        }
        return items[index];
    }

    private void increaseSize() {
        int[] newBits = new int[arraySize + arraySize / 2];
        for (int i = 0; i < arraySize; i++) {
            newBits[i] = items[i];
        }
        items = newBits;
        arraySize += arraySize / 2;
    }

    private void decreaseSize() {
        int[] newBits = new int[arraySize / 2];
        for (int i = 0; i < (arraySize / 4); i++) {
            newBits[i] = items[i];
        }
        items = newBits;
        arraySize /= 2;
    }

}
