package kompakti;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileReader {
    public byte[] readBytes(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        byte[] bytes = inputStream.readAllBytes();
        inputStream.close();
        return bytes;
    }

    public void writeFile(String filename, byte[] bytesToWrite) throws IOException {
        OutputStream outputStream = new FileOutputStream(filename);
        outputStream.write(bytesToWrite);
        outputStream.close();
    }
}

