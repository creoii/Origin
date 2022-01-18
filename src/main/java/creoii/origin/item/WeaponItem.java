package creoii.origin.item;

import com.google.gson.*;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.data.objects.JsonObjects;

public class WeaponItem extends Item implements EquippableItem {
    private final JsonObjects.StatData statBonus;

    public WeaponItem(String id, ItemType type, ItemRarity rarity, JsonObjects.StatData statBonus) {
        super(id, type, rarity);
        this.statBonus = statBonus;
    }

    @Override
    public JsonObjects.StatData getStatBonus() {
        return statBonus;
    }

    public static WeaponItem deserialize(JsonObject object) {
        String id = JsonUtil.getString(object, "id");
        ItemType type = ItemType.valueOf(JsonUtil.getString(object, "type").toUpperCase());
        ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
        JsonObjects.StatData statBonus = JsonObjects.StatData.deserialize(object, "stat_bonus");
        return new WeaponItem(id, type, rarity, statBonus);
    }

    public static JsonElement serialize(Item item, JsonSerializationContext context) {
        if (item instanceof WeaponItem weapon) {
            JsonObject object = new JsonObject();
            object.addProperty("id", weapon.getId());
            object.addProperty("type", weapon.getType().name().toLowerCase());
            object.addProperty("rarity", weapon.getRarity().name().toLowerCase());
            object.add("stat_bonus", JsonObjects.StatData.serialize(weapon.getStatBonus()));
            return object;
        } throw new JsonSyntaxException("Unable to serialize non-weapon item.");
    }
}
