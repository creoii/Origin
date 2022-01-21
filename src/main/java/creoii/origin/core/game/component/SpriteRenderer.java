package creoii.origin.core.game.component;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector2f position;
    private Vector2f scale;
    private Vector4f color;

    public SpriteRenderer(Vector2f position, Vector2f scale, Vector4f color) {
        this.position = position;
        this.scale = scale;
        this.color = color;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Vector4f getColor() {
        return color;
    }

    @Override
    public void start() {
    }

    @Override
    public void update(double deltaTime) {
    }
}
