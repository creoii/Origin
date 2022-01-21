package creoii.origin.core.game;

import creoii.origin.core.game.component.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private final List<Component> components = new ArrayList<>();

    public GameObject(Component... components) {
        for (Component c : components) {
            addComponent(c);
        }
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (Component c : components) {
            if (clazz.isAssignableFrom(c.getClass())) return clazz.cast(c);
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
