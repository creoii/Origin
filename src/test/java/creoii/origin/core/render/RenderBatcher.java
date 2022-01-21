package creoii.origin.core.render;

import creoii.origin.core.display.Window;
import creoii.origin.core.display.camera.Camera;
import creoii.origin.core.game.component.SpriteRenderer;
import creoii.origin.core.util.Transform;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatcher {
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 6;
    private final int VERTEX_BYTES = VERTEX_SIZE * Float.BYTES;

    private SpriteRenderer[] sprites;
    private int spriteCount = 0;
    private boolean hasRoom = true;

    public float[] vertices;
    private int vaoId, vboId;
    private int maxBatchSize;
    private Shader shader;

    public RenderBatcher(int maxBatchSize) {
        shader = new Shader("origin/assets/shaders/default.glsl");
        shader.compile();

        sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];
    }

    public boolean hasRoom() {
        return hasRoom;
    }

    public void start() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        int eboId = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
    }

    public void render() {
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shader.use();
        Camera camera = Window.getScene().getCamera();
        shader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        shader.uploadMat4f("uView", camera.getViewMatrix());

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, spriteCount * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.detach();
    }

    private int[] generateIndices() {
        int[] elements = new int[6 * maxBatchSize];
        for (int i = 0; i < maxBatchSize; ++i) {
            loadElementIndices(elements, i);
        }
        return elements;
    }

    private void loadElementIndices(int[] elements, int i) {
        int offsetIndex = i * 6;
        int offset = i * 4;

        elements[offsetIndex] = offset + 3;
        elements[offsetIndex + 1] = offset + 2;
        elements[offsetIndex + 2] = offset;

        elements[offsetIndex + 3] = offset;
        elements[offsetIndex + 4] = offset + 2;
        elements[offsetIndex + 5] = offset + 1;
    }

    public void addSprite(SpriteRenderer sprite) {
        int i = spriteCount;
        sprites[i] = sprite;
        spriteCount++;

        loadVertexProperties(i);

        if (spriteCount >= maxBatchSize) hasRoom = false;
    }

    private void loadVertexProperties(int i) {
        SpriteRenderer sprite = sprites[i];

        int offset = i * 4 * VERTEX_SIZE;
        Vector4f color = sprite.getColor();

        float x = 1f;
        float y = 1f;
        Transform transform = sprite.getParent().getTransform();
        for (int j = 0; j < 4; j++) {
            if (j == 1) y = 0f;
            else if (j == 2) x = 0f;
            else if (j == 3) y = 1f;

            vertices[offset] = transform.getPosition().x() + (x + transform.getScale().x());
            vertices[offset + 1] = transform.getPosition().y() + (y + transform.getScale().y());

            vertices[offset + 2] = color.x();
            vertices[offset + 3] = color.y();
            vertices[offset + 4] = color.z();
            vertices[offset + 5] = color.w();

            offset += VERTEX_SIZE;
        }
    }
}
