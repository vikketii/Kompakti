package kompakti.util;

public class BitArray {
    private int[] bits;
    private int pointerPosition;
    private int arraySize = 2048;

    public BitArray() {
        bits = new int[arraySize];
        pointerPosition = 0;
    }

    public BitArray(int arraySize) {
        this.arraySize = arraySize;
        bits = new int[arraySize];
        pointerPosition = 0;
    }

    public write(int value) {

    }


}
