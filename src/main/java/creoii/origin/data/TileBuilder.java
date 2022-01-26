package creoii.origin.data;

import com.google.gson.Gson;
import creoii.origin.player.Class;
import creoii.origin.tile.Tile;

import java.io.Reader;

public class TileBuilder extends DataBuilder<Tile> {
    public TileBuilder() {
        super("tiles");
    }

    @Override
    Tile getObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, Tile.class);
    }
}
