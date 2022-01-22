package creoii.origin.core.render;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatcher> batches;

    public Renderer() {
        batches = new ArrayList<>();
    }

    public void add(SpriteRenderer sprite) {
        boolean added = false;
        for (RenderBatcher batch : batches) {
            if (batch.hasRoom()) {
                Texture texture = sprite.getTexture();
                if (texture == null || batch.hasTexture(texture) || batch.hasRoomForTextures()) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatcher newBatch = new RenderBatcher(MAX_BATCH_SIZE).start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }

    public void render(double deltaTime) {
        for (RenderBatcher batch : batches) {
            batch.render();
        }
    }
}
