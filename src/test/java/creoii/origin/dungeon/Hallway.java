package creoii.origin.dungeon;

import org.joml.Vector2i;

public class Hallway extends Room {
    private final int width, length;

    public Hallway(Vector2i pos, int width, int length) {
        super(pos, Shape.RECTANGLE, 0, 0, 1, 1, 0);
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean isHallway() {
        return true;
    }
}
