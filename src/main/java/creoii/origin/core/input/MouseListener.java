package creoii.origin.core.input;

import creoii.origin.core.display.Window;
import creoii.origin.core.display.camera.Camera;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class MouseListener {
    private double scrollX, scrollY;
    private double mouseX, mouseY, lastX, lastY;
    private Vector2f mousePos = new Vector2f(), lastMousePos = new Vector2f();
    private final boolean[] mouseButtonDown = new boolean[3];
    private boolean dragging;

    public static final MouseListener instance = new MouseListener();

    private MouseListener() {
        scrollX = 0d;
        scrollY = 0d;
        mouseX = 0d;
        mouseY = 0d;
        lastX = 0d;
        lastY = 0d;
    }

    public static void cursorPositionCallback(long window, double mouseX, double mouseY) {
        instance.lastX = instance.mouseX;
        instance.lastY = instance.mouseY;
        instance.mouseX = mouseX;
        instance.mouseY = mouseY;
        instance.lastMousePos = new Vector2f(instance.mousePos);
        instance.mousePos.set(mouseX, mouseY);

        instance.dragging = instance.mouseButtonDown[0] || instance.mouseButtonDown[1] || instance.mouseButtonDown[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) instance.mouseButtonDown[button] = true;
        else if (action == GLFW.GLFW_RELEASE) {
            instance.mouseButtonDown[button] = false;
            instance.dragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        instance.scrollX = xOffset;
        instance.scrollY = yOffset;
    }

    public static double getX() {
        return instance.mouseX;
    }
    public static double getY() {
        return instance.mouseY;
    }
    public static Vector2f getMousePos() { return instance.mousePos; }
    public static Vector2f getLastMousePos() { return instance.lastMousePos; }
    public static Vector2f getMousePosWorldSpace() {
        Vector2f camPos = Window.get().getScene().getCamera().getPosition();
        return new Vector2f((float) getX() + camPos.x + 88f, (float) getY() + camPos.y + 20f);
    }
    // not working
    public static boolean isMoving() { return instance.mousePos.x != instance.lastMousePos.x || instance.mousePos.y != instance.lastMousePos.y; }
    public static double getDX() { return instance.lastX - instance.mouseX; }
    public static double getDY() {
        return instance.lastY - instance.mouseX;
    }
    public static boolean isDragging() {
        return instance.dragging;
    }
    public static boolean mouseButtonDown(int button) {
        return instance.mouseButtonDown[button];
    }
    public static double getScrollX() {
        return instance.scrollX;
    }
    public static double getScrollY() {
        return instance.scrollY;
    }
}
