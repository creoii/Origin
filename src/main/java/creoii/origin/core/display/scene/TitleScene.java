package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.Game;
import org.joml.Vector2f;

public class TitleScene extends Scene {
    @Override
    public void start() {
        super.start();
        camera = new Camera(new Vector2f());

        Game.getPlayer().init();
        renderer.add(Game.getPlayer().getSpriteRenderer());
    }

    @Override
    public void update(float deltaTime) {
        renderer.render(deltaTime);
    }
}
