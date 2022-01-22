package creoii.origin.player;

import creoii.origin.core.game.Transform;
import creoii.origin.data.objects.JsonObjects;
import org.joml.Vector2f;

public class Character {
    private Player player;
    private Class clazz;
    private float health;
    private float mana;
    private JsonObjects.StatData currentStats;
    private int level = 1;
    private int xp = 0;
    private Transform transform;

    public Character(Player player, Class clazz) {
        this.player = player;
        this.clazz = clazz;
        currentStats = clazz.getBaseStats();
        health = currentStats.getMaxHealth();
        mana = currentStats.getMaxMana();
        transform = new Transform(new Vector2f(), new Vector2f());
    }

    public Class getClazz() { return clazz; }
    public float getHealth() { return health; }
    public float getMana() { return mana; }
    public int getLevel() { return level; }
    public int getXp() { return xp; }

    public void incrementLevel() {
        ++level;
    }

    public void addXp(int amount) {
        xp += amount;
    }

    public void update(float deltaTime) {

    }
}
