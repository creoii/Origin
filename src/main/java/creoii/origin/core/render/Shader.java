package creoii.origin.core.render;

import creoii.origin.core.Main;
import creoii.origin.core.util.FileUtil;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int programId;
    private boolean inUse;
    private String vertexSource = "";
    private String fragmentSource = "";

    public Shader(String path) {
        try {
            // convert the path into a reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.getFileAsStream(path)));
            List<String> lines = reader.lines().toList();
            String line;
            String type = "";
            // i increments every line; j increments every #type
            for (int i = 0, j = 0; i < lines.size(); ++i) {
                line = lines.get(i);
                if (line.contains("#type")) {
                    type = line.substring(6);
                    ++j;
                    continue;
                }

                // add lines to vertexSource or fragmentSource
                if (j == 1) {
                    if (type.equals("vertex")) vertexSource = vertexSource.concat(line).concat("\n");
                    else if (type.equals("fragment")) fragmentSource = fragmentSource.concat(line).concat("\n");
                }
                if (j == 2) {
                    if (type.equals("vertex")) vertexSource = vertexSource.concat(line).concat("\n");
                    else if (type.equals("fragment")) fragmentSource = fragmentSource.concat(line).concat("\n");
                }
            }
        } catch (IOException e) {
            Main.LOGGER.warning("Unable to open file for shader ".concat(path));
        }
    }

    public Shader compile() {
        int vertexId, fragmentId;

        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);

        programId = glCreateProgram();
        glAttachShader(programId, vertexId);
        glAttachShader(programId, fragmentId);
        glLinkProgram(programId);

        return this;
    }

    public void use() {
        if (!inUse) {
            glUseProgram(programId);
            inUse = true;
        }
    }

    public void detach() {
        if (inUse) {
            glUseProgram(0);
            inUse = false;
        }
    }

    public void uploadMat4f(String name, Matrix4f matrix) {
        int location = glGetUniformLocation(programId, name);
        use();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }

    public void uploadMat3f(String name, Matrix3f matrix) {
        int location = glGetUniformLocation(programId, name);
        use();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        matrix.get(buffer);
        glUniformMatrix3fv(location, false, buffer);
    }

    public void uploadVec4f(String name, Vector4f vec) {
        int location = glGetUniformLocation(programId, name);
        use();
        glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String name, Vector3f vec) {
        int location = glGetUniformLocation(programId, name);
        use();
        glUniform3f(location, vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String name, Vector2f vec) {
        int location = glGetUniformLocation(programId, name);
        use();
        glUniform2f(location, vec.x, vec.y);
    }

    public void uploadFloat(String name, float f) {
        int location = glGetUniformLocation(programId, name);
        use();
        glUniform1f(location, f);
    }

    public void uploadInt(String name, int i) {
        int location = glGetUniformLocation(programId, name);
        use();
        glUniform1i(location, i);
    }

    public void uploadTexture(String name, int id) {
        int location = glGetUniformLocation(programId, name);
        use();
        glUniform1i(location, id);
    }

    public void uploadIntArray(String name, int[] array) {
        int location = glGetUniformLocation(programId, name);
        use();
        glUniform1iv(location, array);
    }
}
