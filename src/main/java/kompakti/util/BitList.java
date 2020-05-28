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


    // Each item is presented as 12 bit in byte array.
    public byte[] getListAsByteArray() {
        int byteArraySize = (itemCount + (itemCount / 2)) + (itemCount % 2 == 0 ? 0 : 1);
        byte[] byteArray = new byte[byteArraySize];

        int byteCount = 0;
        byte helperByte = 0;

        int evenLeftMask = 4080;
        int evenRightMask = 15;
        int oddLeftMask = 3840;
        int oddRightMask = 255;

        for (int i = 0; i < itemCount; i++) {
            if (i % 2 == 0) {
                byte left = (byte) ((bits[i] & evenLeftMask) >> 4);
                byte right = (byte) ((bits[i] & evenRightMask) << 4);
                byteArray[byteCount] = left;
                helperByte = right;
                byteCount++;

                if (i == itemCount - 1) {
                    // If last item
                    byteArray[byteCount] = right;
                }
            } else {
                byte left = (byte) (helperByte + ((bits[i] & oddLeftMask) >> 8));
                byte right = (byte) (bits[i] & oddRightMask);
                byteArray[byteCount] = left;
                byteArray[byteCount + 1] = right;

                byteCount += 2;
            }
        }


        return byteArray;
    }
//    public byte[] getListAsByteArray(int maxValue) throws Exception {
//        if (getMaxItem() > maxValue) {
//            throw new Exception("BitList contains too big values for byte[]");
//        }
//
//        byte[] byteBuffer = new byte[2];
//        for (int i = 0; i < ; i++) {
//
//        }
//
//    }


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
