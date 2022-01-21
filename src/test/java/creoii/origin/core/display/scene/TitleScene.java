package creoii.origin.core.display.scene;

import creoii.origin.core.display.Window;
import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;
import creoii.origin.core.game.component.SpriteRenderer;
import creoii.origin.core.input.KeyListener;
import creoii.origin.core.util.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public class TitleScene extends Scene {
    @Override
    public void init() {
        super.init();

        camera = new Camera(new Vector2f());

        addGameObject(new GameObject(new Transform(new Vector2f(), new Vector2f()), new SpriteRenderer(new Vector4f(1, 1, 1, 1))));
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) Window.get().exit();

        System.out.println("update");
        renderer.render(deltaTime);
    }
}
