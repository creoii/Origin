package creoii.origin.entity;

import creoii.origin.core.game.Collider;
import creoii.origin.core.game.Game;
import creoii.origin.core.game.Transform;
import creoii.origin.core.render.sprite.DynamicSpriteRenderer;
import creoii.origin.core.render.sprite.Sprite;
import creoii.origin.core.util.AssetPool;

public class Entity {
    private final String id;
    private Transform transform;
    private Collider collider;
    private DynamicSpriteRenderer spriteRenderer;

    public Entity(String id) {
        this.id = id;
    }

    public Entity init(Transform transform, Sprite sprite) {
        this.transform = transform;
        spriteRenderer = (DynamicSpriteRenderer) new DynamicSpriteRenderer(sprite).setTransform(getTransform());
        collider = new Collider(getTransform().getPosition(), (int) getTransform().getScale().x, (int) getTransform().getScale().y);
        return this;
    }

    public String getId() { return id; }
    public Transform getTransform() { return transform; }
    public DynamicSpriteRenderer getSpriteRenderer() { return spriteRenderer; }
    public Collider getCollider() { return collider; }

    public enum EntityType {
        PLAYER("player");

        private String id;

        EntityType(String id) {
            this.id = id;
        }

        public String getId() { return id; }
    }
}
