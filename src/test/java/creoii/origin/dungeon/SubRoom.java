package creoii.origin.dungeon;

import org.joml.Vector2i;

public class SubRoom extends Room {
    private final boolean hasHallway;

    public SubRoom(Vector2i pos, Shape shape, int xSize, int ySize, int separation, boolean hasHallway) {
        super(pos, shape, xSize, ySize, separation, 0, 0);
        this.hasHallway = hasHallway;
    }

    public boolean hasHallway() {
        return hasHallway;
    }
}
