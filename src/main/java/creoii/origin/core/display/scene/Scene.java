package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.render.Renderer;

public abstract class Scene {
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean running = false;

    public Camera getCamera() {
        return camera;
    }
    public Renderer getRenderer() { return renderer; }

    public void start() {
        running = true;
    }

    public abstract void update(float deltaTime);
}
