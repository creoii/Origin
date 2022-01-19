package creoii.origin.core;

import creoii.origin.core.display.Window;

public class GameThread extends Thread {
    @Override
    public void run() {
        super.run();
        Window.get().run();
    }
}
