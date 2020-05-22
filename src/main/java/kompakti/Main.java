package kompakti;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String filename = args[0];
            System.out.println(filename);
            ArrayList<Byte> bytes = readBytes(filename);

            ArrayList<Byte> compressed = LZW(bytes);

            present(bytes, compressed);

        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

}
