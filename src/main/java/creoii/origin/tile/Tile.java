package creoii.origin.tile;

import com.google.gson.*;
import creoii.origin.core.Main;
import creoii.origin.core.game.Transform;
import creoii.origin.core.render.sprite.SpriteRenderer;
import creoii.origin.core.util.AssetPool;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.data.Identifiable;
import creoii.origin.data.objects.JsonObjects;
import org.joml.Vector2f;

import java.lang.reflect.Type;

/**
 * Tile has no special properties, it's literally just a sprite
 */
public class Tile implements Identifiable {
    private final String id;
    private final String[] textures;
    private SpriteRenderer sprite;

    public Tile(String id, String[] textures) {
        this.id = id;
        this.textures = textures;
    }

    public Tile init(Vector2f position) {
        sprite = new SpriteRenderer(AssetPool.getTileSprite(textures[Main.RANDOM.nextInt(textures.length)].concat(".png"))).setTransform(new Transform(position, new Vector2f(20, 20)));
        return this;
    }

    @Override
    public String getId() {
        return id;
    }
    public String[] getTextures() { return textures; }
    public SpriteRenderer getSprite() { return sprite; }

    public static class Serializer implements JsonDeserializer<Tile>, JsonSerializer<Tile> {
        @Override
        public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject object = json.getAsJsonObject();
                String id = JsonUtil.getString(object, "id");
                JsonArray textures = JsonUtil.getArray(object, "textures");
                String[] ret;
                if (textures != null && textures.size() > 0) {
                    ret = new String[textures.size()];
                    for (int i = 0; i < textures.size(); ++i) {
                        ret[i] = textures.get(i).getAsString();
                    }
                }
                else ret = new String[]{id};
                if (object.has("stat_modifier")) {
                    JsonObjects.StatData statModifier = JsonObjects.StatData.deserialize(object, "stat_modifier");
                    return new Tile1(id, ret, statModifier);
                }
                return new Tile(id, ret);
            }
            throw new JsonSyntaxException("Not a json object.");
        }

        @Override
        public JsonElement serialize(Tile src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
