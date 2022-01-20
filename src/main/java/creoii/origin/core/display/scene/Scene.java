package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;
import creoii.origin.core.game.component.SpriteRenderer;
import creoii.origin.core.render.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Camera camera;
    private boolean running = false;
    private Renderer renderer = new Renderer();
    private List<GameObject> gameObjects = new ArrayList<>();

    public void init() {
        if (!running) {
            running = true;
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void start() {
        for (GameObject obj : gameObjects) {
            obj.start();
            renderer.add(obj.getComponent(SpriteRenderer.class));
        }
    }

    public GameObject addGameObject(GameObject obj) {
        gameObjects.add(obj);
        if (running) {
            obj.start();
            renderer.add(obj.getComponent(SpriteRenderer.class));
        }
        return null;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public abstract void update(double deltaTime);
}
