package creoii.origin.item;

import com.google.gson.*;
import creoii.origin.data.Identifiable;
import creoii.origin.core.util.JsonUtil;

import java.lang.reflect.Type;
import java.util.function.Function;

public class Item implements Identifiable {
    private final String id;
    private final ItemType type;
    private final ItemRarity rarity;

    public Item(String id, ItemType type, ItemRarity rarity) {
        this.id = id;
        this.type = type;
        this.rarity = rarity;
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

    public static class Serializer implements JsonSerializer<Item>, JsonDeserializer<Item> {
        @Override
        public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonObject()) {
                JsonObject object = json.getAsJsonObject();
                return ItemType.valueOf(JsonUtil.getString(object, "type").toUpperCase()).deserializer.apply(object);
            } throw new JsonSyntaxException("Not a json object.");
        }

        @Override
        public JsonElement serialize(Item src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonObject();
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
        KATANA(0),
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

        public static final int WEAPON_ID = 0;
        public static final int ABILITY_ID = 1;
        public static final int ARMOR_ID = 2;
        public static final int ACCESSORY_ID = 3;
        public static final int SIGIL_ID = 4;
        public static final int CONSUMABLE_ID = 5;
        public static final int TOKEN_ID = 6;
        private final int id;
        private final Function<JsonObject, Item> deserializer;

        ItemType(int id) {
            this.id = id;
            this.deserializer = getDeserializer(id);
        }

        public int getId() {
            return id;
        }

        private static Function<JsonObject, Item> getDeserializer(int id) {
            return switch (id) {
                case WEAPON_ID -> WeaponItem::deserialize;
                case ABILITY_ID -> AbilityItem::deserialize;
                case ARMOR_ID -> ArmorItem::deserialize;
                case ACCESSORY_ID -> AccessoryItem::deserialize;
                case SIGIL_ID -> SigilItem::deserialize;
                case CONSUMABLE_ID -> ConsumableItem::deserialize;
                case TOKEN_ID -> TokenItem::deserialize;
                default -> throw new IllegalStateException("Unknown item type id: ".concat(String.valueOf(id)));
            };
        }
    }
}
