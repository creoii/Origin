package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;
import creoii.origin.core.game.Transform;
import creoii.origin.core.game.component.SpriteRenderer;
import creoii.origin.core.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class TitleScene extends Scene {
    @Override
    public void start() {
        super.start();
        camera = new Camera(new Vector2f());

        addGameObject(new GameObject(new Transform(new Vector2f(100f, 100f), new Vector2f(256f, 256f)), new SpriteRenderer(AssetPool.getTexture("src/main/resources/origin/assets/textures/classes/wizard.png"))));
    }

    @Override
    public void update(float deltaTime) {
        for (GameObject obj : getGameObjects()) {
            obj.update(deltaTime);
        }

        renderer.render(deltaTime);
    }
}
