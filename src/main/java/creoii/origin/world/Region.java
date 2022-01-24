package creoii.origin.world;

import creoii.origin.tile.Tile;
import org.joml.Vector2f;

import java.util.function.Consumer;

public class Region {
    //                   default = 32
    public static final int SIZE = 16;
    private Tile[][] tiles;
    private final Vector2f position;

    public Region(Vector2f position) {
        this.position = position;
        tiles = new Tile[SIZE][SIZE];

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
        //        System.out.println("create tile");
                tiles[i][j] = new Tile("lava0.png");
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    public Vector2f getPosition() { return position; }

    public void forEachTile(Consumer<Tile> action) {
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                action.accept(value);
            }
        }
    }
}
