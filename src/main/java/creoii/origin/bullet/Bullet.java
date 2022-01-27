package creoii.origin.bullet;

import creoii.origin.core.display.Window;
import creoii.origin.core.game.Collider;
import creoii.origin.core.game.Transform;
import creoii.origin.core.render.sprite.DynamicSpriteRenderer;
import creoii.origin.core.render.sprite.Sprite;
import creoii.origin.core.util.MouseUtil;
import creoii.origin.player.Player;
import org.joml.Vector2f;

public class Bullet {
    private final Player parent;
    private DynamicSpriteRenderer spriteRenderer;
    private Collider collider;

    private Vector2f direction;

    public Bullet(Player parent) {
        this.parent = parent;
    }

    public Bullet init(Vector2f position) {
        spriteRenderer = new DynamicSpriteRenderer(new Transform(position, new Vector2f(50f, 50f)), new Sprite(null));
        Window.get().getScene().getRenderer().add(spriteRenderer);
        collider = new Collider(spriteRenderer.getTransform().getPosition(), 8);

        //direction = MouseUtil.getDirectionVector(parent.getSpriteRenderer().getTransform().getPosition());
        return this;
    }

    public void update(float deltaTime) {
        //spriteRenderer.getTransform().getPosition().add(direction);
    }
}