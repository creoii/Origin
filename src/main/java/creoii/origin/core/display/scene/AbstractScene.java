package creoii.origin.core.display.scene;

public abstract class AbstractScene {
    public void init() { System.out.println("Init"); }
    public abstract void update(double deltaTime);
}
