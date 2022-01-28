package creoii.origin.entity.player;

import com.google.gson.*;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.data.Identifiable;
import creoii.origin.item.Item;

import java.lang.reflect.Type;

import static creoii.origin.data.objects.JsonObjects.StatData;

public class Class implements Identifiable {
    private final String id;
    private final String parent;
    private final Item.ItemType[] slotTypes;
    private final StatData baseStats;
    private final StatData minStatIncrease;
    private final StatData maxStatIncrease;

    public Class(String id, String parent, Item.ItemType weaponType, Item.ItemType abilityType, Item.ItemType armorType, StatData baseStats, StatData minStatIncrease, StatData maxStatIncrease) {
        this.id = id;
        this.parent = parent;
        slotTypes = new Item.ItemType[]{weaponType, abilityType, armorType, Item.ItemType.ACCESSORY};
        this.baseStats = baseStats;
        this.minStatIncrease = minStatIncrease;
        this.maxStatIncrease = maxStatIncrease;
    }

    public Class(String id, Item.ItemType weaponType, Item.ItemType abilityType, Item.ItemType armorType, StatData baseStats, StatData minStatIncrease, StatData maxStatIncrease) {
        this(id, null, weaponType, abilityType, armorType, baseStats, minStatIncrease, maxStatIncrease);
    }

    @Override
    public String getId() {
        return id;
    }

    public Item.ItemType[] getSlotTypes() {
        return slotTypes;
    }

    public StatData getBaseStats() {
        return baseStats;
    }

    public StatData getMinStatIncrease() {
        return minStatIncrease;
    }

    public StatData getMaxStatIncrease() {
        return maxStatIncrease;
    }

    public static class Serializer implements JsonDeserializer<Class>, JsonSerializer<Class> {
        @Override
        public Class deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject object = json.getAsJsonObject();
                String id = JsonUtil.getString(object, "id");
                Item.ItemType weaponType = Item.ItemType.valueOf(JsonUtil.getString(object, "weapon_type").toUpperCase());
                Item.ItemType abilityType = Item.ItemType.valueOf(JsonUtil.getString(object, "ability_type").toUpperCase());
                Item.ItemType armorType = Item.ItemType.valueOf(JsonUtil.getString(object, "armor_type").toUpperCase());
                StatData baseStats = StatData.deserialize(object, "base_stats");
                StatData minStatIncrease = StatData.deserialize(object, "min_stat_increase");
                StatData maxStatIncrease = StatData.deserialize(object, "max_stat_increase");
                if (object.has("parent")) {
                    String parent = JsonUtil.getString(object, "parent");
                    return new Class(id, parent, weaponType, abilityType, armorType, baseStats, minStatIncrease, maxStatIncrease);
                } else return new Class(id, weaponType, abilityType, armorType, baseStats, minStatIncrease, maxStatIncrease);
            }
            return null;
        }

        @Override
        public JsonElement serialize(Class src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("id", src.getId());
            object.addProperty("weapon_type", src.getSlotTypes()[0].name());
            object.addProperty("ability_type", src.getSlotTypes()[1].name());
            object.addProperty("armor_type", src.getSlotTypes()[2].name());
            object.add("base_stats", context.serialize(src.getBaseStats()));
            object.add("min_stat_increase", context.serialize(src.getMinStatIncrease()));
            object.add("max_stat_increase", context.serialize(src.getMaxStatIncrease()));
            return object;
        }
    }
}
