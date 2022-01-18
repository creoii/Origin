package creoii.origin.data;

import com.google.gson.Gson;
import creoii.origin.core.Main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDataBuilder<T extends DataObject> {
    private final Map<String, T> values = new HashMap<>();
    private final String path;
    private boolean loaded;

    public AbstractDataBuilder(String name, Gson gson) {
        path = "origin/data/".concat(name).concat("/");
        long startTime = System.nanoTime();
        load(gson);
        long endTime = System.nanoTime();
        if (loaded) {
            DataLoader.BUILDERS.add(this);
            System.out.println("Successfully loaded ".concat(name).concat(" in ").concat(String.valueOf((endTime - startTime) / 1000000d)).concat(" ms"));
        }
    }

    public void load(Gson gson) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getFileAsStream(path)));
            String file;
            while ((file = reader.readLine()) != null) {
                if (file.endsWith(".json")) {
                    T obj = getObject(new BufferedReader(new InputStreamReader(getFileAsStream(path.concat("/".concat(file))))), gson);
                    values.put(obj.getId(), obj);

                    if (Main.debug) Main.debug(file);
                }
            }
            reader.close();
            loaded = true;
        } catch (IOException e) {
            Main.LOGGER.severe("Unable to find " + path);
        }
    }

    private InputStream getFileAsStream(final String path) {
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            // create directory here
            Main.LOGGER.severe("Unable to find or create " + path);
            return null;
        }
        return stream;
    }

    public T getObject(String id) {
        for (String s : values.keySet()) {
            if (s.equals(id)) return values.get(s);
        }
        return null;
    }

    public boolean isLoaded() {
        return loaded;
    }

    abstract T getObject(Reader reader, Gson gson);
}
