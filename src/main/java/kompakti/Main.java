package kompakti;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String fileName = args[0];
            System.out.println(fileName);
            ArrayList<Byte> bytes = readBytes(fileName);

            ArrayList<Byte> compressed = LZW(bytes);

            present(bytes, compressed);

        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

}
