package creoii.origin.entity;

import creoii.origin.core.game.Transform;

public class Entity {
    private final String id;
    private Transform transform;

    public Entity(String id) {
        this.id = id;
    }

    public Entity setTransform(Transform transform) {
        this.transform = transform;
        return this;
    }

    public String getId() { return id; }
    public Transform getTransform() { return transform; }

    public enum EntityType {
        PLAYER("player");

        private String id;

        EntityType(String id) {
            this.id = id;
        }

        public String getId() { return id; }
    }
}
