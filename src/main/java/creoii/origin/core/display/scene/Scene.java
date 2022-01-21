package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;
import creoii.origin.core.render.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean running = false;
    private List<GameObject> gameObjects = new ArrayList<>();

    public Camera getCamera() {
        return camera;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void start() {
        for (GameObject obj : gameObjects) {
            obj.start();
            renderer.add(obj);
        }
        running = true;
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        if (running) {
            obj.start();
            renderer.add(obj);
        }
    }

    public abstract void update(float deltaTime);
}
