package creoii.origin.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import creoii.origin.core.util.JsonUtil;

public class TokenItem extends Item {
    public TokenItem(String id, ItemType type, ItemRarity rarity) {
        super(id, type, rarity);
    }

    public static TokenItem deserialize(JsonObject object) {
        String id = JsonUtil.getString(object, "id");
        ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
        return new TokenItem(id, ItemType.TOKEN, rarity);
    }

    public static JsonElement serialize(Item item, JsonSerializationContext context) {
        if (item instanceof TokenItem token) {
            JsonObject object = new JsonObject();
            object.addProperty("id", token.getId());
            object.addProperty("type", ItemType.TOKEN.name());
            object.addProperty("rarity", token.getRarity().name().toLowerCase());
            return object;
        } throw new JsonSyntaxException("Unable to serialize non-token item.");
    }
}
