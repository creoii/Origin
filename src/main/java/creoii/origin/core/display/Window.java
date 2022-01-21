package creoii.origin.core.display;

import creoii.origin.core.GameSettings;
import creoii.origin.core.Main;
import creoii.origin.core.display.scene.Scene;
import creoii.origin.core.display.scene.TitleScene;
import creoii.origin.core.display.scene.WorldScene;
import creoii.origin.core.input.KeyListener;
import creoii.origin.core.input.MouseListener;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private final String title;
    private final int width, height;
    private int titleBarWidth = 0;
    private float r = 0f, g = 0f, b = 0f, a = 1f;
    private long glfwWindow;

    private static Window instance;
    private static Scene currentScene = new TitleScene();

    public Window() {
        this.title = "Origin";
        this.width = GameSettings.WINDOW_WIDTH;
        this.height = GameSettings.WINDOW_HEIGHT + titleBarWidth;
    }

    public static Window get() {
        return instance == null ? new Window() : instance;
    }

    public Scene getScene() {
        return currentScene;
    }

    public void changeScene(int sceneId) {
        switch (sceneId) {
            case 0 -> currentScene = new TitleScene();
            case 1 -> currentScene = new WorldScene();
            default -> throw new IllegalStateException("Unknown scene id: " + sceneId);
        }
        currentScene.start();
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
        exit();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        if (titleBarWidth > 0) glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);

        glfwWindow = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (glfwWindow == MemoryUtil.NULL) throw new IllegalStateException("Unable to create window");

        ByteBuffer windowBuf = STBImage.stbi_load("src/main/resources/origin/assets/icon_x24.png", new int[]{1}, new int[]{1}, new int[]{1}, 0);
        ByteBuffer taskbarBuf = STBImage.stbi_load("src/main/resources/origin/assets/icon_x48.png", new int[]{1}, new int[]{1}, new int[]{1}, 0);
        if (windowBuf != null && taskbarBuf != null) {
            glfwSetWindowIcon(glfwWindow, GLFWImage.malloc(2).put(GLFWImage.malloc().set(24, 24, windowBuf)).put(GLFWImage.malloc().set(48, 48, taskbarBuf)).flip());
            STBImage.stbi_image_free(windowBuf);
            STBImage.stbi_image_free(taskbarBuf);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(glfwWindow, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(glfwWindow, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::cursorPositionCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyPressedCallback);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();

        Main.DATA_LOADER.loadResources();

        currentScene.start();
    }

    private void loop() {
        float startTime = (float) glfwGetTime();
        float endTime;
        float deltaTime = 0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            GL11.glClearColor(r, g, b, a);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            currentScene.update(deltaTime);

            glfwSwapBuffers(glfwWindow);
            glfwPollEvents();

            endTime = (float) glfwGetTime();
            deltaTime = endTime - startTime;
            startTime = endTime;
        }
    }

    private void exit() {
        Callbacks.glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
