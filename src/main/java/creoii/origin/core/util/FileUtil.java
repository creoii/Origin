package creoii.origin.core.util;

import creoii.origin.core.Main;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static InputStream getFileAsStream(String path) throws IOException {
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            Main.LOGGER.info("Failed to find or create directory ".concat(path));
        }
        return stream;
    }
}
