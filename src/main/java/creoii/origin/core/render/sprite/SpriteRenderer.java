package creoii.origin.core.render.sprite;

import creoii.origin.core.game.Transform;
import creoii.origin.core.render.Texture;
import creoii.origin.entity.Entity;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer {
    private Entity parent;
    private Transform transform;
    protected Vector4f color;
    private Texture texture;
    private Vector2f[] texCoords;
    protected Sprite sprite;
    private boolean shouldRender = false;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite, Vector4f tint) {
        texture = sprite.getTexture();
        texCoords = sprite.getTexCoords();
        color = tint;
        this.sprite = sprite;
    }

    public SpriteRenderer(Sprite sprite) {
        this(sprite, new Vector4f(1f, 1f, 1f, 1f));
    }

    public SpriteRenderer setParent(Entity entity) {
        parent = entity;
        return this;
    }

    public SpriteRenderer setTransform(Transform transform) {
        parent = null;
        this.transform = transform;
        return this;
    }

    public Vector4f getColor() {
        return color;
    }
    public Texture getTexture() { return texture; }
    public Vector2f[] getTexCoords() { return texCoords; }
    public Sprite getSprite() { return sprite; }
    public Transform getTransform() { return parent == null ? transform : parent.getTransform(); }
    public boolean isDynamic() { return false; }
    public boolean shouldRender() { return shouldRender; }
    public void setShouldRender(boolean shouldRender) { this.shouldRender = shouldRender; }
    public void setTexture(Texture texture) { this.texture = texture; }
}
