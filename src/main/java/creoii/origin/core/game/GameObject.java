package creoii.origin.core.game;

import creoii.origin.core.game.component.Component;
import creoii.origin.core.util.Transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameObject {
    private final List<Component> components = new ArrayList<>();
    private Transform transform;

    public GameObject(Transform transform) {
        this.transform = transform;
    }

    public GameObject(Transform transform, Component... components) {
        this(transform);
        for (Component c : components) {
            addComponent(c);
        }
    }

    public Transform getTransform() {
        return transform;
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (Component c : components) {
            if (clazz.isAssignableFrom(c.getClass())) {
                try {
                    return clazz.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> clazz) {
        for (int i = 0; i < components.size(); ++i) {
            if (clazz.isAssignableFrom(components.get(i).getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component component) {
        component.setParent(this);
        components.add(component);
    }

    public void start() {
        for (Component c : components) {
            if (c.shouldUpdate()) c.start();
        }
    }

    public void update(double deltaTime) {
        for (Component c : components) {
            if (c.shouldUpdate()) c.update(deltaTime);
        }
    }
}
