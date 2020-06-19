package kompakti.util;

public class Converter {

    // items are stored as 16 bits in byte array
    public byte[] changeArrayListTo2ByteArray(ArrayList arrayList) {
        int byteArraySize = arrayList.size() * 2;
        byte[] byteArray = new byte[byteArraySize];

        int byteCount = 0;

        int leftMask = 65535 - 255;
        int rightMask = 255;

        for (int i = 0; i < arrayList.size(); i++) {
            byteArray[byteCount] = (byte) ((arrayList.get(i) & leftMask) >> 8);
            byteArray[byteCount + 1] = (byte) ((arrayList.get(i) & rightMask));
            byteCount += 2;
        }

        return byteArray;
    }


    public ArrayList change2ByteArrayToArrayList(byte[] bytes) {
        int itemCount = bytes.length / 2;
        ArrayList arrayList = new ArrayList(itemCount);

        int byteCount = 0;
        for (int i = 0; i < itemCount; i++) {
            int left = bytes[byteCount] < 0 ? bytes[byteCount] + 256 : bytes[byteCount];
            int right = bytes[byteCount + 1] < 0 ? bytes[byteCount + 1] + 256 : bytes[byteCount + 1];
            int value = ((left << 8) + right);
            arrayList.add(value);
            byteCount += 2;
        }

        return arrayList;
    }

    public byte[] changeArrayListToByteArray(ArrayList arrayList) {
        byte[] bytes = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bytes[i] = (byte) arrayList.get(i);
        }
        return bytes;
    }

    public byte[] changeBitListToByteArray(BitList bitList) {
        byte[] bytes = new byte[bitList.size()];
        for (int i = 0; i < bitList.size(); i++) {
            bytes[i] = (byte) bitList.getByte(i);
        }
        return bytes;
    }

    public ArrayList changeByteArrayToArrayList(byte[] bytes) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < bytes.length; i++) {
            arrayList.add(Byte.toUnsignedInt(bytes[i]));
        }
        return arrayList;
    }
}
