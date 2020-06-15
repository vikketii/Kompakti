package kompakti.util;

public class Converter {

    // items are stored as 16 bits in byte array
    public byte[] changeBitListTo2ByteArray(BitList bitList) {
        int byteArraySize = bitList.size() * 2;
        byte[] byteArray = new byte[byteArraySize];

        int byteCount = 0;

        int leftMask = 65535 - 255;
        int rightMask = 255;

        for (int i = 0; i < bitList.size(); i++) {
            byteArray[byteCount] = (byte) ((bitList.get(i) & leftMask) >> 8);
            byteArray[byteCount + 1] = (byte) ((bitList.get(i) & rightMask));
            byteCount += 2;
        }

        return byteArray;
    }


    public BitList change2ByteArrayToBitList(byte[] bytes) {
        int itemCount = bytes.length / 2;
        BitList bitList = new BitList(itemCount);

        int byteCount = 0;
        for (int i = 0; i < itemCount; i++) {
            int left = bytes[byteCount] < 0 ? bytes[byteCount] + 256 : bytes[byteCount];
            int right = bytes[byteCount + 1] < 0 ? bytes[byteCount + 1] + 256 : bytes[byteCount + 1];
            int value = ((left << 8) + right);
            bitList.add(value);
            byteCount += 2;
        }

        return bitList;
    }

    public byte[] changeBitListToByteArray(BitList bitList) {
        byte[] bytes = new byte[bitList.size()];
        for (int i = 0; i < bitList.size(); i++) {
            bytes[i] = (byte) bitList.get(i);
        }
        return bytes;
    }

    public BitList changeByteArrayToBitList(byte[] bytes) {
        BitList bitList = new BitList();
        for (int i = 0; i < bytes.length; i++) {
            bitList.add(Byte.toUnsignedInt(bytes[i]));
        }
        return bitList;
    }
}
