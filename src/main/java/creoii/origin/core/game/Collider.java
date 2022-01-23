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
        min = new Vector2f(-size, -size);
        max = new Vector2f(size, size);
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
        min = min.sub(size, size);
        max = max.add(size, size);
        return this;
    }

    public Collider contract(int size) {
        min = min.add(size, size);
        max = max.sub(size, size);
        return this;
    }
}
