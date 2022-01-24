package creoii.origin.core.util;

import creoii.origin.core.render.Shader;
import creoii.origin.core.render.Texture;
import creoii.origin.core.render.sprite.Sprite;
import creoii.origin.core.render.sprite.Spritesheet;

import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    public static Map<String, Shader> SHADERS = new HashMap<>();
    public static Map<String, Texture> TEXTURES = new HashMap<>();
    private static Map<String, Spritesheet> SPRITESHEETS = new HashMap<>();

    public static Shader getShader(String id) {
        if (SHADERS.containsKey(id)) {
            return SHADERS.get(id);
        } else {
            return SHADERS.put(id, new Shader(id).compile());
        }
    }

    public static Texture getTexture(String id) {
        if (TEXTURES.containsKey(id)) {
            return TEXTURES.get(id);
        } else {
            return TEXTURES.put(id, new Texture(id));
        }
    }

    public static void addSpritesheet(String id, Spritesheet sheet) {
        if (!SPRITESHEETS.containsKey(id)) SPRITESHEETS.put(id, sheet);
    }
    public static void addSpritesheet(String id, int width, int length) {
        addSpritesheet(id, new Spritesheet(id, width, length));
    }

    public static Spritesheet getSpritesheet(String id) {
        return SPRITESHEETS.getOrDefault(id, null);
    }

    public static Sprite getItemSprite(String id) {
        return getSpritesheet(Spritesheet.X8_SHEET).getSprite("src/main/resources/origin/assets/textures/items/".concat(id).concat(".png"));
    }

    public static Sprite getEnemySprite(String id) {
        return getSpritesheet(Spritesheet.X8_SHEET).getSprite("src/main/resources/origin/assets/textures/enemies/".concat(id).concat(".png"));
    }

    public static Sprite getTileSprite(String id) {
        return getSpritesheet(Spritesheet.X8_SHEET).getSprite("src/main/resources/origin/assets/textures/tiles/".concat(id));
    }

    public static Sprite getClassSprite(String id) {
        return getSpritesheet(Spritesheet.X8_SHEET).getSprite("src/main/resources/origin/assets/textures/classes/".concat(id).concat(".png"));
    }
}
