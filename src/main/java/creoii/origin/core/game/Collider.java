package creoii.origin.core.game;

import org.joml.Vector2f;

public class Collider {
    private Vector2f pos;
    private Vector2f min;
    private Vector2f max;

    public Collider(Vector2f pos, Vector2f min, Vector2f max) {
        this.pos = pos;
        this.min = min;
        this.max = max;
    }

    public Collider(Vector2f pos, int size) {
        this.pos = pos;
        float half = size / 2f;
        min = new Vector2f(-half, -half);
        max = new Vector2f(half, half);
    }

    public Vector2f getPos() { return pos; }
    public void setPos(Vector2f pos) { this.pos = pos; }

    public boolean collidingWith(Collider other) {
        return ((pos.x + max.x) > (other.pos.x + other.min.x)) &&
                ((pos.x + min.x) < (other.pos.x + other.max.x)) &&
                ((pos.y + max.y) > (other.pos.y + other.min.y)) &&
                ((pos.y + min.y) < (other.pos.y + other.max.y));
    }

    public Collider expand(int size) {
        float half = size / 2f;
        min = min.sub(half, half);
        max = max.add(half, half);
        return this;
    }

    public Collider contract(int size) {
        float half = size / 2f;
        min = min.add(half, half);
        max = max.sub(half, half);
        return this;
    }
}
