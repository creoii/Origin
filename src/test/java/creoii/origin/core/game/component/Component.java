package creoii.origin.core.game.component;

import creoii.origin.core.game.GameObject;

public abstract class Component {
    private GameObject parent;
    private boolean shouldUpdate = true;

    public GameObject getParent() { return parent; }
    public void setParent(GameObject parent) { this.parent = parent; }

    public boolean shouldUpdate() { return shouldUpdate; }
    public void setShouldUpdate(boolean shouldUpdate) { this.shouldUpdate = shouldUpdate; }

    public abstract void start();
    public abstract void update(double deltaTime);
}
