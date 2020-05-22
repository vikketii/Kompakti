package kompakti;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReader {
    public byte[] readBytes(String filename) throws IOException {
        File fileRead = new File(filename);
        byte[] bytes = Files.readAllBytes(fileRead.toPath());
        return bytes;
    }
}
