package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.Game;
import creoii.origin.tile.Tile;
import creoii.origin.tile.Tile1;
import creoii.origin.world.Region;
import org.joml.Vector2f;

public class TitleScene extends Scene {
    @Override
    public void start() {
        super.start();
        camera = new Camera(new Vector2f());

        Game.getPlayer().init();
        renderer.add(Game.getPlayer().getSpriteRenderer());
        Game.getWorld().forEachRegion(region -> {
            for (int i = 0; i < Region.SIZE; ++i) {
                for (int j = 0; j < Region.SIZE; ++j) {
                    Tile tile = region.getTiles()[i][j];
                    if (tile != null) renderer.add(tile.init(new Vector2f(region.getPosition().x + (i * 20), region.getPosition().y + (j * 20))).getSprite());
                }
            }
        });
    }

    @Override
    public void update(float deltaTime) {
        renderer.render(deltaTime);
        camera.update(deltaTime);
    }
}
