package creoii.origin.bullet;

import creoii.origin.core.display.Window;
import creoii.origin.core.game.Collider;
import creoii.origin.core.game.Game;
import creoii.origin.core.game.Transform;
import creoii.origin.core.render.sprite.DynamicSpriteRenderer;
import creoii.origin.core.render.sprite.Sprite;
import creoii.origin.core.util.MouseUtil;
import creoii.origin.entity.player.Player;
import org.joml.Vector2f;

public class Bullet {
    private final int intId;
    private final Player parent;
    private DynamicSpriteRenderer spriteRenderer;
    private Collider collider;
    private float lifetime;

    private Vector2f direction;

    public Bullet(int intId, Player parent, float lifetime) {
        this.intId = intId;
        this.parent = parent;
        this.lifetime = lifetime;
    }

    public int getId() { return intId; }

    public Bullet init(Vector2f position) {
        spriteRenderer = (DynamicSpriteRenderer) new DynamicSpriteRenderer(new Sprite(null)).setTransform(new Transform(position, new Vector2f(50f, 50f)));
        Window.get().getScene().getRenderer().add(spriteRenderer);
        collider = new Collider(spriteRenderer.getTransform().getPosition(), 8);
        direction = MouseUtil.getDirectionVector(position);
        return this;
    }

    public void update(float deltaTime) {
        //spriteRenderer.getTransform().getPosition().add(direction);
        if ((lifetime -= deltaTime) <= 0) Game.getWorld().removeBullet(this);
    }
}
