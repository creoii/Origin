package creoii.origin.core.render;

import creoii.origin.core.Main;
import creoii.origin.core.util.FileUtil;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;

public class Shader {
    private int programId;
    private String vertexSource = "";
    private String fragmentSource = "";
    private final String path;

    public Shader(String path) {
        this.path = path;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.getFileAsStream(path)));
            List<String> lines = reader.lines().toList();
            String line;
            String type = "";
            for (int i = 0, j = 0; i < lines.size(); ++i) {
                line = lines.get(i);
                if (line.contains("#type")) {
                    type = line.substring(6);
                    ++j;
                    continue;
                }

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

    public void compile() {
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
    }

    public void use() {
        glUseProgram(programId);
    }

    public void detach() {
        glUseProgram(0);
    }

    public void uploadMat4f(String name, Matrix4f matrix) {
        int location = glGetUniformLocation(programId, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }
}
