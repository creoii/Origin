package creoii.origin.core.util;

import creoii.origin.core.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class FileUtil {
    public static InputStream getFileAsStream(String path) throws IOException {
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            Main.LOGGER.info("Failed to find or create directory ".concat(path));
        }
        return stream;
    }

    /**
     * @param path - use in the format origin/*
     */
    public static void forEachFileName(String path, Consumer<String> action) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.getFileAsStream(path)));
            String file;
            while ((file = reader.readLine()) != null) {
                action.accept(file);
            }
            reader.close();
        } catch (IOException e) {
            Main.LOGGER.warning("Unable to find ".concat(path));
        }
    }
}
