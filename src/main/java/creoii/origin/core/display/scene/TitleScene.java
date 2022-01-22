package creoii.origin.core.display.scene;

import creoii.origin.core.Main;
import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;
import creoii.origin.core.game.Transform;
import creoii.origin.core.game.component.SpriteRenderer;
import creoii.origin.core.render.Spritesheet;
import creoii.origin.core.util.AssetPool;
import org.joml.Vector2f;

public class TitleScene extends Scene {
    @Override
    public void start() {
        super.start();
        camera = new Camera(new Vector2f());

        addGameObject(new GameObject(new Transform(new Vector2f(100f, 100f), new Vector2f(256f, 256f)), new SpriteRenderer(AssetPool.getSpritesheet(Spritesheet.X8_SHEET).getSprite(Main.RANDOM.nextInt(5)))));
        addGameObject(new GameObject(new Transform(new Vector2f(400f, 100f), new Vector2f(128f, 128f)), new SpriteRenderer(AssetPool.getSpritesheet(Spritesheet.X8_SHEET).getSprite(Main.RANDOM.nextInt(5)))));
    }

    @Override
    public void update(float deltaTime) {
        for (GameObject obj : getGameObjects()) {
            obj.update(deltaTime);
        }
        renderer.render(deltaTime);
    }
}
