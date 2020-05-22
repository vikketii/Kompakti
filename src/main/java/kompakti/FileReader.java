package kompakti;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileReader {
    public byte[] readBytes(String filename) throws IOException {
        File fileRead = new File(filename);
        byte[] bytes = Files.readAllBytes(fileRead.toPath());
        return bytes;
    }

    public String readString(String filename) throws IOException {
        File fileRead = new File(filename);
        String content = Files.readString(fileRead.toPath());
        return content;
    }
}
