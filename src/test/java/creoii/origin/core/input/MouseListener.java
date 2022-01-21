package creoii.origin.core.input;

import org.lwjgl.glfw.GLFW;

public class MouseListener {
    private double scrollX, scrollY;
    private double mouseX, mouseY, lastX, lastY;
    private final boolean[] mouseButtonDown = new boolean[3];
    private boolean dragging;

    private static MouseListener instance;

    private MouseListener() {
        scrollX = 0d;
        scrollY = 0d;
        mouseX = 0d;
        mouseY = 0d;
        lastX = 0d;
        lastY = 0d;
    }

    public static MouseListener get() {
        return instance == null ? new MouseListener() : instance;
    }

    public static void mousePositionCallback(long window, double mouseX, double mouseY) {
        get().lastX = get().mouseX;
        get().lastY = get().mouseY;
        get().mouseX = mouseX;
        get().mouseY = mouseY;

        get().dragging = get().mouseButtonDown[0] || get().mouseButtonDown[1] || get().mouseButtonDown[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) get().mouseButtonDown[button] = true;
        else if (action == GLFW.GLFW_RELEASE) {
            get().mouseButtonDown[button] = false;
            get().dragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public double getX() {
        return get().mouseX;
    }

    public double getY() {
        return get().mouseY;
    }

    public double getDX() {
        return get().lastX - get().mouseX;
    }

    public double getDY() {
        return get().lastY - get().mouseX;
    }

    public boolean isDragging() {
        return get().dragging;
    }

    public boolean mouseButtonDown(int button) {
        return get().mouseButtonDown[button];
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }
}
