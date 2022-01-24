package creoii.origin.player;

import creoii.origin.core.game.Collider;
import creoii.origin.core.game.Game;
import creoii.origin.core.game.GameSettings;
import creoii.origin.core.game.Transform;
import creoii.origin.core.input.KeyListener;
import creoii.origin.core.render.sprite.DynamicSpriteRenderer;
import creoii.origin.core.util.AssetPool;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int characterSlots = 2;
    private List<Character> characters = new ArrayList<>();
    private DynamicSpriteRenderer spriteRenderer;
    private Collider collider;

    public Player(String name) {
        this.name = name;
    }

    public void init() {
        spriteRenderer = new DynamicSpriteRenderer(new Transform(new Vector2f(600f, 325f), new Vector2f(50f, 50f)), AssetPool.getClassSprite(Game.getActiveCharacter().getCharacterClass().getId()));
        collider = new Collider(spriteRenderer.getTransform().getPosition(), 10);
    }

    public String getName() { return name; }
    public int getCharacterSlots() { return characterSlots; }
    public List<Character> getCharacters() { return characters; }
    public DynamicSpriteRenderer getSpriteRenderer() { return spriteRenderer; }
    public Collider getCollider() { return collider; }

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
        if (KeyListener.isKeyPressed(GameSettings.MOVE_FORWARDS)) spriteRenderer.getTransform().getPosition().add(0f, deltaTime * 250f);
        if (KeyListener.isKeyPressed(GameSettings.MOVE_BACKWARDS)) spriteRenderer.getTransform().getPosition().add(0f, deltaTime * -250f);
        if (KeyListener.isKeyPressed(GameSettings.MOVE_RIGHT)) spriteRenderer.getTransform().getPosition().add(deltaTime * 250f, 0f);
        if (KeyListener.isKeyPressed(GameSettings.MOVE_LEFT)) spriteRenderer.getTransform().getPosition().add(deltaTime * -250f, 0f);

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_1)) {
            Game.setActiveCharacter(0);
        }
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_2)) {
            Game.setActiveCharacter(1);
        }

        spriteRenderer.update(deltaTime);
        if (!collider.getPos().equals(spriteRenderer.getTransform().getPosition())) collider.setPos(spriteRenderer.getTransform().getPosition());
    }
}
