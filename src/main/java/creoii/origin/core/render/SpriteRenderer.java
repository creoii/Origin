package creoii.origin.core.render;

import creoii.origin.core.game.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer {
    private Vector4f color;
    private Texture texture;
    private Vector2f[] texCoords;
    private Sprite sprite;
    private Transform transform;
    private Transform lastTransform = new Transform(new Vector2f(), new Vector2f());
    private boolean dirty = true;

    public SpriteRenderer(Transform transform, Vector4f color) {
        this.transform = transform;
        this.color = color;
        sprite = new Sprite(null);
    }

    public SpriteRenderer(Transform transform, Sprite sprite) {
        this.transform = transform;
        texture = sprite.getTexture();
        texCoords = sprite.getTexCoords();
        color = new Vector4f(1f, 1f, 1f, 1f);
    }

    public Vector4f getColor() {
        return color;
    }
    public Texture getTexture() { return texture; }
    public Vector2f[] getTexCoords() { return texCoords; }
    public Transform getTransform() { return transform; }
    public boolean isDirty() { return dirty; }
    public void setDirty(boolean dirty) { this.dirty = dirty; }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        dirty = true;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color.set(color);
            dirty = true;
        }
    }

    public void update(double deltaTime) {
        if (!lastTransform.equals(transform)) {
            dirty = true;
        }
        transform.copy(lastTransform);
    }
}
