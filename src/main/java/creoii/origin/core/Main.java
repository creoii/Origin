package creoii.origin.core;

import creoii.origin.core.display.Window;
import creoii.origin.data.DataLoader;

import java.util.logging.Logger;

public class Main {
    public static final Logger LOGGER = Logger.getGlobal();
    public static final DataLoader DATA_LOADER = new DataLoader();
    public static final boolean debug = false;

    public static void main(String[] args) {
        Window.get().run();
    }

    public static void sendDebug(String message) {
        System.out.println("[DEBUG]: ".concat(message));
    }
}
