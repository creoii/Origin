package creoii.origin.data.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.origin.core.util.JsonUtil;

public class JsonObjects {
    public static class StatData {
        public int maxHealth, maxMana, speed, attackSpeed, healthRegen, manaRegen, attack, armor;

        public StatData(int maxHealth, int maxMana, int speed, int attackSpeed, int healthRegen, int manaRegen, int attack, int armor) {
            this.maxHealth = maxHealth;
            this.maxMana = maxMana;
            this.speed = speed;
            this.attackSpeed = attackSpeed;
            this.healthRegen = healthRegen;
            this.manaRegen = manaRegen;
            this.attack = attack;
            this.armor = armor;
        }

        public int getMaxHealth() { return maxHealth; }
        public int getMaxMana() { return maxMana; }
        public int getSpeed() { return speed; }
        public int getAttackSpeed() { return attackSpeed; }
        public int getHealthRegen() { return healthRegen; }
        public int getManaRegen() { return manaRegen; }
        public int getAttack() { return attack; }
        public int getArmor() { return armor; }

        public void add(StatData other) {
            maxHealth += other.maxHealth;
            maxMana += other.maxMana;
            speed += other.speed;
            attackSpeed += other.attackSpeed;
            healthRegen += other.healthRegen;
            manaRegen += other.manaRegen;
            attack += other.attack;
            armor += other.armor;
        }

        public static StatData deserialize(JsonObject object, String name) {
            if (object.has(name)) {
                JsonObject object1 = object.getAsJsonObject(name);
                int maxHealth = JsonUtil.getInt(object1, "max_health", 0);
                int maxMana = JsonUtil.getInt(object1, "max_mana", 0);
                int speed = JsonUtil.getInt(object1, "speed", 0);
                int attackSpeed = JsonUtil.getInt(object1, "attack_speed", 0);
                int healthRegen = JsonUtil.getInt(object1, "health_regen", 1);
                int manaRegen = JsonUtil.getInt(object1, "mana_regen", 0);
                int attack = JsonUtil.getInt(object1, "attack", 0);
                int armor = JsonUtil.getInt(object1, "armor", 0);
                return new StatData(maxHealth, maxMana, speed, attackSpeed, healthRegen, manaRegen, attack, armor);
            } return new StatData(0, 0, 0, 0, 0, 0, 0, 0);
        }

        public static JsonElement serialize(StatData src) {
            JsonObject object = new JsonObject();
            object.addProperty("max_health", src.getMaxHealth());
            object.addProperty("max_mana", src.getMaxMana());
            object.addProperty("speed", src.getSpeed());
            object.addProperty("attack_speed", src.getAttackSpeed());
            object.addProperty("health_regen", src.getHealthRegen());
            object.addProperty("mana_regen", src.getManaRegen());
            object.addProperty("attack", src.getAttack());
            object.addProperty("armor", src.getArmor());
            return object;
        }
    }

    public record PositionData(float x, float y) {
        public static PositionData deserialize(JsonObject object, String name) {
            if (object.has(name)) {
                JsonObject object1 = object.getAsJsonObject(name);
                float x = JsonUtil.getFloat(object1, "x", 0f);
                float y = JsonUtil.getFloat(object1, "y", 0f);
                return new PositionData(x, y);
            } return new PositionData(0f, 0f);
        }

        public static JsonElement serialize(PositionData positionData) {
            JsonObject object = new JsonObject();
            object.addProperty("x", positionData.x());
            object.addProperty("y", positionData.y());
            return object;
        }
    }
}
