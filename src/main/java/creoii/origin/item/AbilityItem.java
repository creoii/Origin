package creoii.origin.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.data.objects.JsonObjects;

public class AbilityItem extends Item implements EquippableItem {
    private final JsonObjects.StatData statBonus;

    public AbilityItem(String id, ItemType type, ItemRarity rarity, JsonObjects.StatData statBonus) {
        super(id, type, rarity);
        this.statBonus = statBonus;
    }

    @Override
    public JsonObjects.StatData getStatBonus() {
        return statBonus;
    }

    public static AbilityItem deserialize(JsonObject object) {
        String id = JsonUtil.getString(object, "id");
        ItemType type = ItemType.valueOf(JsonUtil.getString(object, "type").toUpperCase());
        ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
        JsonObjects.StatData statBonus = JsonObjects.StatData.deserialize(object, "stat_bonus");
        return new AbilityItem(id, type, rarity, statBonus);
    }

    public static JsonElement serialize(Item item, JsonSerializationContext context) {
        if (item instanceof AbilityItem ability) {
            JsonObject object = new JsonObject();
            object.addProperty("id", ability.getId());
            object.addProperty("type", ability.getType().name().toLowerCase());
            object.addProperty("rarity", ability.getRarity().name().toLowerCase());
            object.add("stat_bonus", JsonObjects.StatData.serialize(ability.getStatBonus()));
            return object;
        } throw new JsonSyntaxException("Unable to serialize non-ability item.");
    }
}
