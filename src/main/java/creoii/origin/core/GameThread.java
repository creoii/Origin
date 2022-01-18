package creoii.origin.core;

import creoii.origin.core.display.Window;

public class GameThread extends Thread {
    @Override
    public synchronized void start() {
        super.start();
        Window.get().run();
    }
}
