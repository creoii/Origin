package creoii.origin.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.origin.core.Main;
import creoii.origin.core.util.FileUtil;
import creoii.origin.item.Item;
import creoii.origin.player.Class;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDataBuilder<T extends Identifiable> {
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.getFileAsStream(path)));
            String file;
            while ((file = reader.readLine()) != null) {
                if (file.endsWith(".json")) {
                    T obj = getObject(new BufferedReader(new InputStreamReader(FileUtil.getFileAsStream(path.concat("/".concat(file))))), gson);
                    values.put(obj.getId(), obj);

                    if (Main.debug) Main.sendDebug(file);
                }
            }
            reader.close();
            loaded = true;
        } catch (IOException e) {
            Main.LOGGER.severe("Unable to find " + path);
        }
    }

    public T getObject(String id) {
        for (String s : values.keySet()) {
            if (s.equals(id)) return values.get(s);
        }
        return null;
    }

    public Map<String, T> getValues() {
        return values;
    }

    public boolean isLoaded() {
        return loaded;
    }

    abstract T getObject(Reader reader, Gson gson);

    public static class ItemBuilder extends AbstractDataBuilder<Item> {
        public ItemBuilder() {
            super("items", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Item.class, new Item.Serializer()).create());
        }

        @Override
        Item getObject(Reader reader, Gson gson) {
            return gson.fromJson(reader, Item.class);
        }
    }

    public static class ClassBuilder extends AbstractDataBuilder<Class> {
        public ClassBuilder() {
            super("classes", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Class.class, new Class.Serializer()).create());
        }

        @Override
        Class getObject(Reader reader, Gson gson) {
            return gson.fromJson(reader, Class.class);
        }
    }
}
