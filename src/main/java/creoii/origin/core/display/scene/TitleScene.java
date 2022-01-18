package creoii.origin.core.display.scene;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.ARBVertexArrayObject.*;

public class TitleScene extends AbstractScene {
    private final String vertexShaderSrc = """
            #version 330 core
            layout(location = 0) in vec3 aPos;
            layout(location = 1) in vec4 aColor;

            out vec4 fColor;

            void main() {
                fColor = aColor;
                gl_Position = vec4(aPos, 1f);
            }""";
    private final String fragmentShaderSrc = """
            #version 330 core

            in vec4 fColor;

            out vec4 color;

            void main() {
                color = fColor;
            }""";

    private int vertexId, fragmentId, shaderProgram;

    private final float[] vertexArray = {
       // x     y   z       r   g   b    a
         .5f, -.5f, 0f,     1f, 0f, 0f,  1f,
        -.5f,  .5f, 0f,     0f, 1f, 0f,  1f,
         .5f,  .5f, 0f,     1f, 0f, 1f,  1f,
        -.5f, -.5f, 0f,     1f, 1f, 0f,  1f
    };
    private final int[] elementArray = {
            2, 1, 0,
            0, 1, 3,
    };

    private int vaoId, vboId, eboId;

    @Override
    public void init() {
        super.init();

        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, vertexShaderSrc);
        glCompileShader(vertexId);

        int success = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.out.println(glGetShaderInfoLog(vertexId, len));
        }

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, fragmentShaderSrc);
        glCompileShader(fragmentId);

        success = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH);
            System.out.println(glGetShaderInfoLog(fragmentId, len));
        }

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexId);
        glAttachShader(shaderProgram, fragmentId);
        glLinkProgram(shaderProgram);

        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
        }

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray);

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
        glUseProgram(shaderProgram);
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glUseProgram(0);
    }
}
