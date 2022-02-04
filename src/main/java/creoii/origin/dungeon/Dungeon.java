package creoii.origin.dungeon;

import com.google.gson.*;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.data.Identifiable;

import java.lang.reflect.Type;

public class Dungeon implements Identifiable {
    private final String id;
    private final boolean test;
    private final int minPaths;
    private final int maxPaths;
    private final int minRoomsPerPath;
    private final int maxRoomsPerPath;
    private final int treasureRoomCount;
    private int xBounds = -1;
    private int yBounds = -1;
    private Path[] paths;

    public Dungeon(String id, boolean test, int minPaths, int maxPaths, int minRoomsPerPath, int maxRoomsPerPath, int treasureRoomCount) {
        this.id = id;
        this.test = test;
        this.minPaths = minPaths;
        this.maxPaths = maxPaths;
        this.minRoomsPerPath = minRoomsPerPath;
        this.maxRoomsPerPath = maxRoomsPerPath;
        this.treasureRoomCount = treasureRoomCount;
    }

    public Dungeon withBounds(int x, int y) {
        xBounds = x;
        yBounds = y;
        return this;
    }

    @Override
    public String getId() { return id; }
    public boolean isTest() { return test; }
    public Path[] getPaths() { return paths; }

    public static class Serializer implements JsonDeserializer<Dungeon>, JsonSerializer<Dungeon> {
        @Override
        public Dungeon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject object = json.getAsJsonObject();
                String id = JsonUtil.getString(object, "id");
                boolean test = JsonUtil.getBoolean(object, "test", false);
                int minPaths = JsonUtil.getInt(object, "min_paths", 0);
                int maxPaths = JsonUtil.getInt(object, "max_paths", 0);
                int minRoomsPerPath = JsonUtil.getInt(object, "min_rooms_per_path", 0);
                int maxRoomsPerPath = JsonUtil.getInt(object, "max_rooms_per_path", 0);
                int treasureRoomCount = JsonUtil.getInt(object, "treasure_room_count", 0);
                Dungeon dungeon = new Dungeon(id, test, minPaths, maxPaths, minRoomsPerPath, maxRoomsPerPath, treasureRoomCount);

                if (object.has("x_bounds") && object.has("y_bounds")) {
                    int xBounds = JsonUtil.getInt(object, "x_bounds", -1);
                    int yBounds = JsonUtil.getInt(object, "y_bounds", -1);
                    dungeon = dungeon.withBounds(xBounds, yBounds);
                }

                return dungeon;
            }
            throw new JsonSyntaxException("Not a json object.");
        }

        @Override
        public JsonElement serialize(Dungeon src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
