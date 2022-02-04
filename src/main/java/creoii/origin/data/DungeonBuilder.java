package creoii.origin.data;

import com.google.gson.Gson;
import creoii.origin.dungeon.Dungeon;

import java.io.Reader;

public class DungeonBuilder extends DataBuilder<Dungeon> {
    public DungeonBuilder() {
        super("dungeons");
    }

    @Override
    Dungeon getObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, Dungeon.class);
    }
}
