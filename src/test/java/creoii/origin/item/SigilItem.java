package creoii.origin.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import creoii.origin.core.util.JsonUtil;

public class SigilItem extends Item {
    public SigilItem(String id, ItemType type, ItemRarity rarity) {
        super(id, type, rarity);
    }

    public static SigilItem deserialize(JsonObject object) {
        String id = JsonUtil.getString(object, "id");
        ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
        return new SigilItem(id, ItemType.SIGIL, rarity);
    }

    public static JsonElement serialize(Item item, JsonSerializationContext context) {
        if (item instanceof SigilItem sigil) {
            JsonObject object = new JsonObject();
            object.addProperty("id", sigil.getId());
            object.addProperty("type", ItemType.SIGIL.name());
            object.addProperty("rarity", sigil.getRarity().name().toLowerCase());
            return object;
        } throw new JsonSyntaxException("Unable to serialize non-sigil item.");
    }
}
