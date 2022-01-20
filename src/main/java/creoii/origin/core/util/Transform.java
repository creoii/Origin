package creoii.origin.core.util;

import org.joml.Vector2f;

public class Transform {
    private Vector2f position;
    private Vector2f scale;

    public Transform(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }
}
