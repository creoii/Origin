package creoii.origin.core.game.component;

import creoii.origin.core.render.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector4f color;
    private Texture texture;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.texture = null;
    }

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
        this.color = new Vector4f(1f, 1f, 1f, 1f);
    }

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
