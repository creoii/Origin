package creoii.origin.core.game;

import creoii.origin.data.DataLoader;
import creoii.origin.player.Character;
import creoii.origin.player.Player;

public class Game {
    private static Player player;
    private static Character activeCharacter;

    public Game() {
        player = new Player("test");
        player.createCharacter(DataLoader.CLASSES.getObject("wizard"));
        activeCharacter = player.getCharacter(0);
    }

    public static Player getPlayer() { return player; }
    public static Character getActiveCharacter() { return activeCharacter; }
    public static void setActiveCharacter(int charSlot) { activeCharacter = player.getCharacter(charSlot); }

    public void update(float deltaTime) {
        player.update(deltaTime);
        activeCharacter.update(deltaTime);
    }
}
