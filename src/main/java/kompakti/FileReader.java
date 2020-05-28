package kompakti;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileReader {
    public byte[] readBytes(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);

        long fileSize = new File(filename).length();
        byte[] bytes = new byte[(int) fileSize];

        inputStream.read(bytes);

        return bytes;
    }

    public void writeFile(String filename, byte[] bytesToWrite) throws IOException {
        OutputStream outputStream = new FileOutputStream(filename);
        outputStream.write(bytesToWrite);
    }

//    public String readString(String filename) throws IOException {
//        File fileRead = new File(filename);
//        String content = Files.readString(fileRead.toPath());
//        return content;
//    }
//
//    public void writeString(String filename, ArrayList<Integer> dataToWrite) throws IOException {
//        Writer wr = new FileWriter(filename);
//        for (int i : dataToWrite) {
//            wr.write(Integer.toString(i));
//        }
//        wr.close();
//    }
}

