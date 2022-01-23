package creoii.origin.world;

import creoii.origin.tile.Tile;
import org.joml.Vector2f;

import java.util.function.Consumer;

public class Region {
    private Tile[][] tiles;
    private Vector2f position;

    public Region(Vector2f position) {
        this.position = position;
        tiles = new Tile[32][32];

        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles[i].length; ++j) {
                tiles[i][j] = new Tile("creo_ground0.png");
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
