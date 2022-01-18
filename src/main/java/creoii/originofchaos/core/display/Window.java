package creoii.originofchaos.core.display;

import creoii.originofchaos.core.input.KeyListener;
import creoii.originofchaos.core.input.MouseListener;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

public class Window {
    private String title;
    private int width, height;
    private long glfwWindow;

    private static Window instance;

    public Window() {
        this.title = "Origin of Chaos";
        this.width = 1080;
        this.height = 720;
    }

    public static Window get() {
        return instance == null ? new Window() : instance;
    }

    public void run() {
        init();
        loop();

        Callbacks.glfwFreeCallbacks(glfwWindow);
        GLFW.glfwDestroyWindow(glfwWindow);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        glfwWindow = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (glfwWindow == MemoryUtil.NULL) throw new IllegalStateException("Unable to create window");

        GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePositionCallback);
        GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        GLFW.glfwSetKeyCallback(glfwWindow, KeyListener::keyPressedCallback);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(glfwWindow, pWidth, pHeight);

            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(glfwWindow, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        GLFW.glfwMakeContextCurrent(glfwWindow);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(glfwWindow);
    }

    private void loop() {
        GL.createCapabilities();
        GL11.glClearColor(0f, 0f, 0f, 1f);

        while (!GLFW.glfwWindowShouldClose(glfwWindow)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) GLFW.glfwSetWindowShouldClose(glfwWindow, true);

            GLFW.glfwSwapBuffers(glfwWindow);
            GLFW.glfwPollEvents();
        }
    }
}
