package creoii.originofchaos.core;

import creoii.originofchaos.core.display.Window;

public class GameThread extends Thread {
    @Override
    public synchronized void start() {
        super.start();
        Window.get().run();
    }
}
