package creoii.origin.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.data.objects.JsonObjects;

public class AccessoryItem extends Item implements EquippableItem {
    private final JsonObjects.StatData statBonus;

    public AccessoryItem(String id, ItemType type, ItemRarity rarity, JsonObjects.StatData statBonus) {
        super(id, type, rarity);
        this.statBonus = statBonus;
    }

    @Override
    public JsonObjects.StatData getStatBonus() {
        return statBonus;
    }

    public static AccessoryItem deserialize(JsonObject object) {
        String id = JsonUtil.getString(object, "id");
        ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
        JsonObjects.StatData statBonus = JsonObjects.StatData.deserialize(object, "stat_bonus");
        return new AccessoryItem(id, ItemType.ACCESSORY, rarity, statBonus);
    }

    public static JsonElement serialize(Item item, JsonSerializationContext context) {
        if (item instanceof AccessoryItem accessory) {
            JsonObject object = new JsonObject();
            object.addProperty("id", accessory.getId());
            object.addProperty("type", ItemType.ACCESSORY.name());
            object.addProperty("rarity", accessory.getRarity().name().toLowerCase());
            object.add("stat_bonus", JsonObjects.StatData.serialize(accessory.getStatBonus()));
            return object;
        } throw new JsonSyntaxException("Unable to serialize non-accessory item.");
    }
}
