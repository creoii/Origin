package creoii.origin.player.character;

import creoii.origin.data.objects.JsonObjects;
import creoii.origin.player.Class;

public class Character {
    private Class clazz;
    private float health;
    private float mana;
    private JsonObjects.StatData currentStats;
    private int level;
    private float xp;
}
