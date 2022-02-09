package creoii.origin.data.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.origin.core.Main;
import creoii.origin.core.util.JsonUtil;
import creoii.origin.core.util.MathUtil;

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

        public void add(StatData add, StatData max) {
            maxHealth = MathUtil.addWithCeil(maxHealth, add.maxHealth, max.maxHealth);
            maxMana = MathUtil.addWithCeil(maxMana, add.maxMana, max.maxMana);
            speed = MathUtil.addWithCeil(speed, add.speed, max.speed);
            attackSpeed = MathUtil.addWithCeil(attack, add.attackSpeed, max.attackSpeed);
            healthRegen = MathUtil.addWithCeil(healthRegen, add.healthRegen, max.healthRegen);
            manaRegen = MathUtil.addWithCeil(manaRegen, add.manaRegen, max.manaRegen);
            attack = MathUtil.addWithCeil(attack, add.attack, max.attack);
            armor = MathUtil.addWithCeil(armor, add.armor, max.armor);
        }

        public void addRandomBetween(StatData minimum, StatData maximum, StatData max) {
            maxHealth = MathUtil.addWithCeil(maxHealth, Main.RANDOM.nextInt(maximum.maxHealth) + minimum.maxHealth, max.maxHealth);
            maxMana = MathUtil.addWithCeil(maxMana, Main.RANDOM.nextInt(maximum.maxMana) + minimum.maxMana, max.maxMana);
            speed = MathUtil.addWithCeil(speed, Main.RANDOM.nextInt(maximum.speed) + minimum.speed, max.speed);
            attackSpeed = MathUtil.addWithCeil(attackSpeed, Main.RANDOM.nextInt(maximum.attackSpeed) + minimum.attackSpeed, max.attackSpeed);
            healthRegen = MathUtil.addWithCeil(healthRegen, Main.RANDOM.nextInt(maximum.healthRegen) + minimum.healthRegen, max.healthRegen);
            manaRegen = MathUtil.addWithCeil(manaRegen, Main.RANDOM.nextInt(maximum.manaRegen) + minimum.manaRegen, max.manaRegen);
            attack = MathUtil.addWithCeil(attack, Main.RANDOM.nextInt(maximum.attack) + minimum.attack, max.attack);
            armor = MathUtil.addWithCeil(armor, Main.RANDOM.nextInt(maximum.armor) + minimum.armor, max.armor);
        }

        public static StatData deserialize(JsonObject object, String name) {
            if (object.has(name)) {
                JsonObject object1 = object.getAsJsonObject(name);
                int maxHealth = JsonUtil.getInt(object1, "max_health", 0);
                int maxMana = JsonUtil.getInt(object1, "max_mana", 0);
                int speed = JsonUtil.getInt(object1, "speed", 0);
                int attackSpeed = JsonUtil.getInt(object1, "attack_speed", 0);
                int healthRegen = JsonUtil.getInt(object1, "health_regen", 0);
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
