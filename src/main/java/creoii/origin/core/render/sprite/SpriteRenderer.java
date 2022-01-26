package creoii.origin.core.render.sprite;

import creoii.origin.core.game.Transform;
import creoii.origin.core.render.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer {
    protected Vector4f color;
    private Texture texture;
    private Vector2f[] texCoords;
    protected Sprite sprite;
    private Transform transform;
    private boolean shouldRender = false;

    public SpriteRenderer(Transform transform, Vector4f color) {
        this.transform = transform;
        this.color = color;
        sprite = new Sprite(null);
    }

    public SpriteRenderer(Transform transform, Sprite sprite, Vector4f tint) {
        this.transform = transform;
        texture = sprite.getTexture();
        texCoords = sprite.getTexCoords();
        color = tint;
        this.sprite = sprite;
    }

    public SpriteRenderer(Transform transform, Sprite sprite) {
        this(transform, sprite, new Vector4f(1f, 1f, 1f, 1f));
    }

    public Vector4f getColor() {
        return color;
    }
    public Texture getTexture() { return texture; }
    public Vector2f[] getTexCoords() { return texCoords; }
    public Sprite getSprite() { return sprite; }
    public Transform getTransform() { return transform; }
    public boolean isDynamic() { return false; }
    public boolean shouldRender() { return shouldRender; }
    public void setShouldRender(boolean shouldRender) { this.shouldRender = shouldRender; }

    public void setTexture(Texture texture) { this.texture = texture; }
}
