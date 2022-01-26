package creoii.origin.core.render;

import creoii.origin.core.render.sprite.DynamicSpriteRenderer;
import creoii.origin.core.render.sprite.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatcher> batches;

    public Renderer() {
        batches = new ArrayList<>();
    }

    public void add(SpriteRenderer sprite) {
        for (RenderBatcher batch : batches) {
            if (batch.hasRoom()) {
                Texture texture = sprite.getTexture();
                if (sprite.isDynamic()) {
                    texture = sprite.getSprite().getTexture();
                }
                if (texture == null || batch.hasTexture(texture) || batch.hasRoomForTextures()) {
                    batch.addSprite(sprite);
                    return;
                }
            }
        }

        RenderBatcher newBatch = new RenderBatcher(MAX_BATCH_SIZE).start();
        batches.add(newBatch);
        newBatch.addSprite(sprite);
    }

    public void render(double deltaTime) {
        for (RenderBatcher batch : batches) {
            batch.render();
        }
    }
}
