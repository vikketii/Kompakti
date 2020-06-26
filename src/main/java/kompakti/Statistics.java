package kompakti;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class Statistics {
    File[] files;

    public Statistics() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL dirURL = classLoader.getResource("canterbury-corpus/");
        File dir = new File(dirURL.getPath());
        files = dir.listFiles();
    }

    public void generate() {

    }
}
