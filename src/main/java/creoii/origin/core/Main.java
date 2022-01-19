package creoii.origin.core;

import creoii.origin.data.DataLoader;
import creoii.origin.item.EquippableItem;

import java.util.logging.Logger;

public class Main {
    public static final Logger LOGGER = Logger.getGlobal();
    public static final DataLoader DATA_LOADER = new DataLoader();
    public static final boolean debug = false;

    public static void main(String[] args) {
        if (DATA_LOADER.isComplete()) {
            new GameThread().start();
        }

        DataLoader.ITEMS.getValues().values().forEach(item -> {
            //if (item instanceof EquippableItem equippable) System.out.println(equippable.getStatBonus());
        });
    }

    public static void sendDebug(String message) {
        System.out.println("[DEBUG]: ".concat(message));
    }
}
