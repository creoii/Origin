package creoii.originofchaos.item;

import com.google.gson.*;
import creoii.originofchaos.data.DataObject;
import creoii.originofchaos.data.objects.JsonObjects;
import creoii.originofchaos.core.util.JsonUtil;

import java.lang.reflect.Type;

public class Item implements DataObject {
    private final String id;
    private final ItemType type;
    private final ItemRarity rarity;
    private final JsonObjects.StatData statBonus;

    public Item(String id, ItemType type, ItemRarity rarity, JsonObjects.StatData statBonus) {
        this.id = id;
        this.type = type;
        this.rarity = rarity;
        this.statBonus = statBonus;
    }

    @Override
    public String getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public JsonObjects.StatData getStatBonus() {
        return statBonus;
    }

    public static class Serializer implements JsonSerializer<Item>, JsonDeserializer<Item> {
        @Override
        public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject object = json.getAsJsonObject();
                String id = JsonUtil.getString(object, "id");
                ItemType type = ItemType.valueOf(JsonUtil.getString(object, "type").toUpperCase());
                ItemRarity rarity = ItemRarity.valueOf(JsonUtil.getString(object, "rarity", "COMMON").toUpperCase());
                JsonObjects.StatData statBonus = JsonObjects.StatData.deserialize(object, "stat_bonus");
                return new Item(id, type, rarity, statBonus);
            }
            return null;
        }

        @Override
        public JsonElement serialize(Item src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("id", src.getId());
            object.addProperty("type", src.getType().name().toLowerCase());
            object.addProperty("rarity", src.getRarity().name().toLowerCase());
            object.add("stat_bonus", context.serialize(src.getStatBonus()));
            return object;
        }
    }

    public enum ItemRarity {
        COMMON,
        RARE,
        MYTHICAL,
        DIVINE,
        ARTIFACT
    }

    public enum ItemType {
        STAFF(0),
        SWORD(0),
        WAND(0),
        BOW(0),
        DAGGER(0),
        HELM(1),
        SHIELD(1),
        SPEAR(1),
        BOOK(1),
        SKULL(1),
        ORB(1),
        SPELL(1),
        SCEPTER(1),
        TOTEM(1),
        QUIVER(1),
        TRAP(1),
        INSTRUMENT(1),
        CLOAK(1),
        POISON(1),
        GAUNTLET(1),
        STAR(1),
        BANNER(1),
        SHEATH(1),
        ARMOR(2),
        ACCESSORY(3),
        SIGIL(4),
        CONSUMABLE(5),
        TOKEN(6);

        private final int id;

        ItemType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
