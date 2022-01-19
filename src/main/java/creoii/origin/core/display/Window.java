package creoii.origin.core.display;

import creoii.origin.core.display.scene.AbstractScene;
import creoii.origin.core.display.scene.TitleScene;
import creoii.origin.core.display.scene.WorldScene;
import creoii.origin.core.input.KeyListener;
import creoii.origin.core.input.MouseListener;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private String title;
    private int width, height;
    private float r = 0f, g = 0f, b = 0f, a = 1f;
    private long glfwWindow;

    private static Window instance;
    private static AbstractScene currentScene = new TitleScene();

    public Window() {
        this.title = "Origin of Chaos";
        this.width = 1080;
        this.height = 720;
    }

    public static Window get() {
        return instance == null ? new Window() : instance;
    }

    public void changeScene(int sceneId) {
        switch (sceneId) {
            case 0 -> currentScene = new TitleScene();
            case 1 -> currentScene = new WorldScene();
            default -> throw new IllegalStateException("Unknown scene id: " + sceneId);
        }
        currentScene.init();
    }

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void run() {
        init();
        loop();

        Callbacks.glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindow = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (glfwWindow == MemoryUtil.NULL) throw new IllegalStateException("Unable to create window");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyPressedCallback);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(glfwWindow, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(glfwWindow, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();

        currentScene.init();
    }

    private void loop() {
        double startTime = glfwGetTime();
        double endTime;
        double deltaTime = 0d;

        while (!glfwWindowShouldClose(glfwWindow)) {
            GL11.glClearColor(r, g, b, a);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            currentScene.update(deltaTime);

            glfwSwapBuffers(glfwWindow);
            glfwPollEvents();

            endTime = glfwGetTime();
            deltaTime = endTime - startTime;
            startTime = endTime;
        }
    }
}
