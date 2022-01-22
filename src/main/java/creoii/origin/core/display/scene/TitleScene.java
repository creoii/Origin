package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.Game;
import creoii.origin.tile.Tile;
import org.joml.Vector2f;

public class TitleScene extends Scene {
    @Override
    public void start() {
        super.start();
        camera = new Camera(new Vector2f());

        Game.getPlayer().init();
        renderer.add(Game.getPlayer().getSpriteRenderer());
        Game.getWorld().forEachRegion(region -> {
            for (int i = 0; i < region.getTiles().length; ++i) {
                for (int j = 0; j < region.getTiles()[i].length; ++j) {
                    Tile tile = region.getTiles()[i][j];
                    tile.init(new Vector2f(i * 20, j * 20));
                    renderer.add(tile.getSprite());
                }
            }
        });
    }

    @Override
    public void update(float deltaTime) {
        renderer.render(deltaTime);
    }
}
