package creoii.origin.entity.player;

import creoii.origin.core.game.Game;
import creoii.origin.core.game.GameSettings;
import creoii.origin.core.game.Transform;
import creoii.origin.core.input.KeyListener;
import creoii.origin.core.input.MouseListener;
import creoii.origin.core.util.AssetPool;
import creoii.origin.entity.Entity;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private final String name;
    private int characterSlots = 2;
    private List<Character> characters = new ArrayList<>();
    private final PlayerController controller;

    public Player(String name) {
        super(EntityType.PLAYER.getId());
        this.name = name;
        controller = new PlayerController(this);
    }

    public Entity init(Transform transform) {
        return super.init(transform, AssetPool.getClassSprite(Game.getActiveCharacter().getCharacterClass().getId()));
    }

    public String getName() { return name; }
    public int getCharacterSlots() { return characterSlots; }
    public List<Character> getCharacters() { return characters; }
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
        if (KeyListener.isKeyPressed(GameSettings.MOVE_FORWARDS)) getSpriteRenderer().getTransform().getPosition().add(0f, deltaTime * 250f);
        if (KeyListener.isKeyPressed(GameSettings.MOVE_BACKWARDS)) getSpriteRenderer().getTransform().getPosition().add(0f, deltaTime * -250f);
        if (KeyListener.isKeyPressed(GameSettings.MOVE_RIGHT)) getSpriteRenderer().getTransform().getPosition().add(deltaTime * 250f, 0f);
        if (KeyListener.isKeyPressed(GameSettings.MOVE_LEFT)) getSpriteRenderer().getTransform().getPosition().add(deltaTime * -250f, 0f);

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_1)) {
            Game.setActiveCharacter(0);
        }
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_2)) {
            Game.setActiveCharacter(1);
        }

        if (MouseListener.mouseButtonDown(0)) {
            controller.useWeapon();
        }

        controller.update(deltaTime);
        getSpriteRenderer().update(deltaTime);
        if (!getCollider().getPos().equals(getSpriteRenderer().getTransform().getPosition())) getCollider().setPos(getSpriteRenderer().getTransform().getPosition());
    }
}
