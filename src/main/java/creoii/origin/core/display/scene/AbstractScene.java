package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;

public abstract class AbstractScene {
    protected Camera camera;

    public void init() { }
    public abstract void update(double deltaTime);
}
