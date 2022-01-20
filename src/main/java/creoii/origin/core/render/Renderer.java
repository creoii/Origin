package creoii.origin.core.render;

import creoii.origin.core.game.component.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatcher> batchers;

    public Renderer() {
        batchers = new ArrayList<>();
    }

    public void add(SpriteRenderer sprite) {
        boolean added = false;
        for (RenderBatcher b : batchers) {
            if (b.hasRoom()) {
                b.addSprite(sprite);
                added = true;
                break;
            }
        }

        if (!added) {
            RenderBatcher add = new RenderBatcher(MAX_BATCH_SIZE);
            add.start();
            batchers.add(add);
            add.addSprite(sprite);
        }
    }

    public void render(double deltaTime) {
        for (RenderBatcher b : batchers) {
            b.render();
        }
    }
}
