package creoii.origin.core.display.scene;

import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.render.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.ARBVertexArrayObject.*;

public class TitleScene extends AbstractScene {
    private final float[] vertexArray = {
       // x     y   z       r    g    b    a
         50.5f, -50.5f, 0f,     1f, .5f,  0f,  1f,
        -50.5f,  50.5f, 0f,     0f,  1f, .5f,  1f,
         50.5f,  50.5f, 0f,     1f, .5f,  1f,  1f,
        -50.5f, -50.5f, 0f,     1f,  1f, .5f,  1f
    };
    private final int[] elementArray = {
            2, 1, 0,
            0, 1, 3,
    };

    private int vaoId, vboId, eboId;
    private Shader defaultShader;

    @Override
    public void init() {
        super.init();

        this.camera = new Camera(new Vector2f(0f, 0f));

        defaultShader = new Shader("origin/assets/shaders/default.glsl");
        defaultShader.compile();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionsSize = 3;
        int colorSize = 4;
        int floatBytes = 4;
        int vertexBytes = (positionsSize + colorSize) * floatBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexBytes, positionsSize * floatBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(double deltaTime) {
        camera.getPosition().sub((float) deltaTime * 50f, 0f);

        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        defaultShader.detach();
    }
}
