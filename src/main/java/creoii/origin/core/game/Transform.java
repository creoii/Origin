package creoii.origin.core.game;

import org.joml.Vector2f;

public class Transform {
    private Vector2f position;
    private Vector2f scale;

    public Transform(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Vector2f getPosition() { return position; }

    public Vector2f getScale() { return scale; }

    public Transform copy() { return new Transform(position, scale); }

    public void copyTo(Transform to) {
        to.position.set(position);
        to.scale.set(scale);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (o instanceof Transform transform) {
            return transform.getPosition().equals(position) && transform.getScale().equals(scale);
        }
        return false;
    }
}
