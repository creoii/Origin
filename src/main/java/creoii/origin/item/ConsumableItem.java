package creoii.origin.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import creoii.origin.core.util.JsonUtil;

public class ConsumableItem extends Item {
    public ConsumableItem(String id, ItemType type, ItemRarity rarity) {
        super(id, type, rarity);
    }

    public static ConsumableItem deserialize(JsonObject object) {
        String id = JsonUtil.getString(object, "id");
        ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
        return new ConsumableItem(id, ItemType.CONSUMABLE, rarity);
    }

    public static JsonElement serialize(Item item, JsonSerializationContext context) {
        if (item instanceof ConsumableItem consumable) {
            JsonObject object = new JsonObject();
            object.addProperty("id", consumable.getId());
            object.addProperty("type", ItemType.CONSUMABLE.name());
            object.addProperty("rarity", consumable.getRarity().name().toLowerCase());
            return object;
        } throw new JsonSyntaxException("Unable to serialize non-consumable item.");
    }
}
