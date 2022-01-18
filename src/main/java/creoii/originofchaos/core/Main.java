package creoii.originofchaos.core;

import creoii.originofchaos.data.DataLoader;

import java.util.logging.Logger;

public class Main {
    public static final Logger LOGGER = Logger.getGlobal();
    public static final DataLoader DATA_LOADER = new DataLoader();
    public static final boolean debug = false;

    public static void main(String[] args) {
        if (DATA_LOADER.isComplete()) {
            new GameThread().start();
        }
    }

    public static void debug(String message) {
        System.out.println("[DEBUG]: ".concat(message));
    }
}
