package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Camera camera;
    private boolean running = false;
    private List<GameObject> gameObjects = new ArrayList<>();

    public void init() {
        if (!running) {
            running = true;
        }
    }

    public void start() {
        gameObjects.forEach(GameObject::start);
    }

    public GameObject addGameObject(GameObject obj) {
        gameObjects.add(obj);
        if (running) {
            obj.start();
        }
        return null;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public abstract void update(double deltaTime);
}
