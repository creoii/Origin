package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.GameObject;
import creoii.origin.core.game.component.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class TitleScene extends Scene {
    @Override
    public void start() {
        super.start();
        camera = new Camera(new Vector2f());

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float)(600 - xOffset * 2);
        float totalHeight = (float)(300 - yOffset * 2);
        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeight / 100.0f;
        for (int x = 0; x < 100; ++x) {
            for (int y = 0; y < 100; ++y) {
                float xPos = xOffset + (x * sizeX);
                float yPos = yOffset + (y * sizeY);
                addGameObject(new GameObject(new SpriteRenderer(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY), new Vector4f(xPos / totalWidth, yPos / totalHeight, .5f, 1f))));
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        for (GameObject obj : getGameObjects()) {
            obj.update(deltaTime);
        }

        renderer.render(deltaTime);
    }
}
