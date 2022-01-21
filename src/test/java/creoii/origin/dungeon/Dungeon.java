package creoii.origin.dungeon;

import creoii.origin.core.Main;
import creoii.origin.data.Identifiable;

import java.util.List;

public class Dungeon implements Identifiable {
    private final String id;
    private final Room[] rooms;
    private List<Hallway> hallways;

    public Dungeon(String id, int minRooms, int maxRooms) {
        this.id = id;
        rooms = new Room[Main.RANDOM.nextInt(maxRooms) + minRooms];
    }

    @Override
    public String getId() {
        return id;
    }
}
