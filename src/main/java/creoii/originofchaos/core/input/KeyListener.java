package creoii.originofchaos.core.input;

import org.lwjgl.glfw.GLFW;

public class KeyListener {
    private final boolean[] keyPressed = new boolean[350];

    private static final KeyListener instance = new KeyListener();

    public static void keyPressedCallback(long window, int key, int scanCode, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) instance.keyPressed[key] = true;
        else if (action == GLFW.GLFW_RELEASE) instance.keyPressed[key] = false;
    }

    public static boolean isKeyPressed(int key) {
        return instance.keyPressed[key];
    }
}
