package creoii.origin.dungeon;

import creoii.origin.core.Main;
import org.joml.Vector2i;
import org.joml.Vector4i;

public class Room {
    private final Vector2i pos;
    private final Shape shape;
    private final int xSize, ySize, separation;
    private final int maxConnects;
    private final SubRoom[] subRooms;

    public Room(Vector2i pos, Shape shape, int xSize, int ySize, int separation, int maxConnects, int maxSubRooms) {
        this.pos = pos;
        this.shape = shape;
        this.xSize = xSize;
        this.ySize = ySize;
        this.separation = separation;
        this.maxConnects = maxConnects;
        subRooms = new SubRoom[Main.RANDOM.nextInt(maxSubRooms)];
    }

    public Vector2i getPos() {
        return pos;
    }

    /**
     * Returns the bounds of the room; used in generation to ensure that rooms don't collide.
     */
    public Vector4i getBounds() {
        if (separation >= 0) {
            Vector2i pos = this.pos.sub(separation, separation);
            int xSize = this.xSize + separation * 2;
            int ySize = this.ySize + separation * 2;
            return new Vector4i(pos.x, pos.y, pos.x + xSize, pos.y + ySize);
        } return new Vector4i(pos.x, pos.y, pos.x + xSize, pos.y + ySize);
    }

    public Shape getShape() {
        return shape;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getSeparation() {
        return separation;
    }

    public int getMaxConnects() {
        return maxConnects;
    }

    public boolean isHallway() {
        return false;
    }

    public enum Shape {
        RECTANGLE,
        ELLIPSE
    }
}
