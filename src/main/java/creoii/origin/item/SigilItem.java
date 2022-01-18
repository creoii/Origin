package creoii.origin.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.data.objects.JsonObjects;

public class SigilItem extends Item {
    public SigilItem(String id, ItemType type, ItemRarity rarity) {
        super(id, type, rarity);
    }

    public static SigilItem deserialize(JsonObject object) {
        String id = JsonUtil.getString(object, "id");
        ItemType type = ItemType.valueOf(JsonUtil.getString(object, "type").toUpperCase());
        ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
        return new SigilItem(id, type, rarity);
    }

    public static JsonElement serialize(Item item, JsonSerializationContext context) {
        if (item instanceof SigilItem weapon) {
            JsonObject object = new JsonObject();
            object.addProperty("id", item.getId());
            object.addProperty("type", item.getType().name().toLowerCase());
            object.addProperty("rarity", item.getRarity().name().toLowerCase());
            return object;
        } throw new JsonSyntaxException("Unable to serialize non-sigil item.");
    }
}
