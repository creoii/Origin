package creoii.origin.core.render;

import creoii.origin.core.Main;
import org.joml.Vector2f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Spritesheet {
    public static final String X8_SHEET = "src/main/resources/origin/assets/spritesheets/sprites_x8.png";
    public static final String X16_SHEET = "src/main/resources/origin/assets/spritesheets/sprites_x16.png";

    private DynamicTexture texture;
    private List<Sprite> sprites;
    private final String path;
    private final int textureWidth, textureHeight;

    public Spritesheet(String path, int textureWidth, int textureHeight) {
        texture = new DynamicTexture(path);
        sprites = new ArrayList<>();
        this.path = path;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void load() {
        texture.load(textureWidth, textureHeight);
        if (texture.getTextures().size() > 0) {
            int x = 0;
            int y = texture.getTextures().get(0).getHeight() - textureHeight;

            for (Texture texture : texture.getTextures()) {
                float topY = (y + textureHeight) / (float) texture.getHeight();
                float bottomY = y / (float) texture.getHeight();
                float rightX = (x + textureWidth) / (float) texture.getWidth();
                float leftX = x / (float) texture.getWidth();

                sprites.add(new Sprite(texture, new Vector2f[]{
                        new Vector2f(rightX, topY),
                        new Vector2f(rightX, bottomY),
                        new Vector2f(leftX, bottomY),
                        new Vector2f(leftX, topY)
                }));

                x += textureWidth;
                if (x >= texture.getWidth()) {
                    x = 0;
                    y -= textureHeight;
                }
            }

            try {
                texture.export("png");
            } catch (IOException e) {
                Main.LOGGER.warning("Unable to export spritesheet at ".concat(path));
            }
        }
    }

    public void addTexture(Texture texture) {
        this.texture.getTextures().add(texture);
    }

    public String getPath() { return path; }

    public Sprite getSprite(int i) {
        if (i >= sprites.size()) return sprites.get(0);
        return sprites.get(i);
    }
}
