package creoii.origin.tile;

import creoii.origin.core.game.Transform;
import creoii.origin.core.render.sprite.SpriteRenderer;
import creoii.origin.core.util.AssetPool;
import creoii.origin.data.Identifiable;
import org.joml.Vector2f;

public class Tile implements Identifiable {
    private final String id;
    private SpriteRenderer sprite;

    public Tile(String id) {
        this.id = id;
    }

    public Tile init(Vector2f position) {
        sprite = new SpriteRenderer(new Transform(position, new Vector2f(20, 20)), AssetPool.getTileSprite(id));
        return this;
    }

    @Override
    public String getId() {
        return id;
    }
    public SpriteRenderer getSprite() { return sprite; }
}
