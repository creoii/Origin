package creoii.origin.player;

import creoii.origin.core.game.Transform;
import creoii.origin.core.render.SpriteRenderer;
import creoii.origin.core.render.Spritesheet;
import creoii.origin.core.util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int characterSlots = 2;
    private List<Character> characters = new ArrayList<>();
    private SpriteRenderer sprite;

    public Player(String name) {
        this.name = name;
    }

    public void init() {
        sprite = new SpriteRenderer(new Transform(new Vector2f(100f, 100f), new Vector2f(256f, 256f)), AssetPool.getSpritesheet(Spritesheet.X8_SHEET).getSprite(0));
    }

    public String getName() { return name; }
    public int getCharacterSlots() { return characterSlots; }
    public List<Character> getCharacters() { return characters; }
    public SpriteRenderer getSpriteRenderer() { return sprite; }

    public void addCharacterSlot() { ++characterSlots; }

    public Character getCharacter(int slot) {
        return characters.get(slot);
    }

    public void createCharacter(Class clazz) {
        if (characters.size() < characterSlots) {
            characters.add(new Character(this, clazz));
        }
    }

    public void update(float deltaTime) {
        sprite.update(deltaTime);
    }
}
