package creoii.origin.core.game.component;

import creoii.origin.core.game.Transform;
import creoii.origin.core.render.Sprite;
import creoii.origin.core.render.Spritesheet;
import creoii.origin.core.render.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector4f color;
    private Texture texture;
    private Vector2f[] texCoords;
    private Sprite sprite;
    private Transform lastTransform;
    private boolean dirty = true;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite) {
        texture = sprite.getTexture();
        texCoords = sprite.getTexCoords();
        color = new Vector4f(1f, 1f, 1f, 1f);
    }

    public Vector4f getColor() {
        return color;
    }

    public Texture getTexture() { return texture; }

    public Vector2f[] getTexCoords() { return texCoords; }

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

    @Override
    public void start() {
        lastTransform = getParent().getTransform().copy();
    }

    @Override
    public void update(double deltaTime) {
        if (!lastTransform.equals(getParent().getTransform())) {
            getParent().getTransform().copy(lastTransform);
        }
    }
}
