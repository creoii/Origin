package creoii.origin.tile;

import creoii.origin.core.game.Collider;
import creoii.origin.data.objects.JsonObjects;
import org.joml.Vector2f;

/**
 * Tile1 can affect entities on top of it - dealing damage, healing, affecting stats, etc
 */
public class Tile1 extends Tile {
    private final JsonObjects.StatData statModifier;
    private Collider collider;

    public Tile1(String id, String[] textures, JsonObjects.StatData statModifier) {
        super(id, textures);
        this.statModifier = statModifier;
    }

    @Override
    public Tile init(Vector2f position) {
        super.init(position);
        collider = new Collider(getSprite().getTransform().getPosition(), 10);
        return this;
    }

    public JsonObjects.StatData getStatModifier() { return statModifier; }
    public Collider getCollider() { return collider; }
}
