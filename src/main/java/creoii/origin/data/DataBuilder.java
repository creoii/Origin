package creoii.origin.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.origin.core.Main;
import creoii.origin.core.util.FileUtil;
import creoii.origin.dungeon.Dungeon;
import creoii.origin.entity.player.Class;
import creoii.origin.item.Item;
import creoii.origin.tile.Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public abstract class DataBuilder<T extends Identifiable> {
    protected final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Item.class, new Item.Serializer()).registerTypeAdapter(Class.class, new Class.Serializer()).registerTypeAdapter(Tile.class, new Tile.Serializer()).registerTypeAdapter(Dungeon.class, new Dungeon.Serializer()).create();
    private final Map<String, T> values = new HashMap<>();
    private final String path;

    private T cachedValue = null;

    public DataBuilder(String name) {
        path = "origin/data/".concat(name).concat("/");
        long startTime = System.nanoTime();
        load();
        long endTime = System.nanoTime();
        DataLoader.BUILDERS.add(this);
        System.out.println("Successfully loaded ".concat(name).concat(" in ").concat(String.valueOf((endTime - startTime) / 1000000d)).concat(" ms"));
    }

    public void load() {
        FileUtil.forEachFileName(path, file -> {
            try {
                if (file.endsWith(".json")) {
                    T obj = getObject(new BufferedReader(new InputStreamReader(FileUtil.getFileAsStream(path.concat("/".concat(file))))), GSON);
                    values.put(obj.getId(), obj);

                    if (Main.debug) Main.sendDebug(file);
                }
            } catch (IOException e) {
                Main.LOGGER.severe("Unable to find " + path);
            }
        });
    }

    public T getObject(String id) {
        if (cachedValue != null) {
            if (cachedValue.getId().equals(id)) return cachedValue;
        }

        for (String s : values.keySet()) {
            if (s.equals(id)) return cachedValue = values.get(s);
        }
        return cachedValue = null;
    }

    public Map<String, T> getValues() {
        return values;
    }

    abstract T getObject(Reader reader, Gson gson);
}
