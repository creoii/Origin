package creoii.origin.core.game.component;

import creoii.origin.core.render.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector2f position;
    private Vector2f scale;
    private Vector4f color;
    private Vector2f[] texCoords;
    private Texture texture;

    public SpriteRenderer(Vector2f position, Vector2f scale, Vector4f color) {
        this.position = position;
        this.scale = scale;
        this.color = color;
        this.texture = null;
    }

    public SpriteRenderer(Vector2f position, Vector2f scale, Texture texture) {
        this.position = position;
        this.scale = scale;
        this.texture = texture;
        this.color = new Vector4f(1f, 1f, 1f, 1f);
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() { return scale; }

    public Vector4f getColor() {
        return color;
    }

    public Texture getTexture() { return texture; }

    public Vector2f[] getTexCoords() {
        return new Vector2f[] {
                new Vector2f(1f, 1f),
                new Vector2f(1f, 0f),
                new Vector2f(0f, 0f),
                new Vector2f(0f, 1f)
        };
    }

    @Override
    public void start() {
    }

    @Override
    public void update(double deltaTime) {
    }
}
