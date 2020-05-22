package kompakti;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileReader {
    public ArrayList<Byte> readBytes(String filename) throws IOException {
        File fileRead = new File(filename);
        byte[] bytes = Files.readAllBytes(fileRead.toPath());
        ArrayList<Byte> bytesAsArrayList = new ArrayList<>(Arrays.asList(bytes));
        return bytesAsArrayList;
    }
}
