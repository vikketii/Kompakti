package kompakti;

import java.util.ArrayList;
import kompakti.FileReader;
import kompakti.LZW;

public class Main {
    public static void main(String[] args) {
        try {
            String filename = args[0];
            System.out.println(filename);
            FileReader fr = new FileReader();
            LZW lzw = new LZW();

            ArrayList<Byte> bytes = fr.readBytes(filename);

            ArrayList<Byte> compressed = lzw.compress(bytes);

//            present(bytes, compressed);

        } catch (Exception e) {
            System.out.println("Give filename as argument");
        }
    }

}
