package creoii.origin.core.render.sprite;

import creoii.origin.core.render.Texture;
import org.joml.Vector2f;

public class Sprite {
    private Texture texture;
    private Vector2f[] texCoords;

    public Sprite(Texture texture, Vector2f[] texCoords) {
        this.texture = texture;
        this.texCoords = texCoords;
    }

    public Sprite(Texture texture) {
        this(texture, new Vector2f[] {
                new Vector2f(1f, 1f),
                new Vector2f(1f, 0f),
                new Vector2f(0f, 0f),
                new Vector2f(0f, 1f)
        });
    }

    public Texture getTexture() { return texture; }

    public Vector2f[] getTexCoords() { return texCoords; }
}
