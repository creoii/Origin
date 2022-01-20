package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;
import creoii.origin.core.game.component.SpriteRenderer;
import creoii.origin.core.util.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class TitleScene extends Scene {
    @Override
    public void init() {
        super.init();

        camera = new Camera(new Vector2f());

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float)(600 - xOffset * 2);
        float totalHeight = (float)(300 - yOffset * 2);
        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeight / 100.0f;
        float padding = 3;

        for (int y = 0; y < 100; ++y) {
            for (int x = 0; x < 100; ++x) {
                float xPos = xOffset + (x * sizeX) + (padding * x);
                float yPos = yOffset + (y * sizeY) + (padding * y);

                GameObject go = new GameObject(new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                go.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
                this.addGameObject(go);
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject obj : getGameObjects()) {
            obj.update(deltaTime);
        }

        getRenderer().render(deltaTime);
    }
}
