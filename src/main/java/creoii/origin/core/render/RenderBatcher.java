package creoii.origin.core.render;

import creoii.origin.core.display.Window;
import creoii.origin.core.game.Transform;
import creoii.origin.core.render.sprite.DynamicSpriteRenderer;
import creoii.origin.core.render.sprite.SpriteRenderer;
import creoii.origin.core.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatcher {
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int TEX_COORDS_SIZE = 2;
    private final int TEX_ID_SIZE = 1;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 9;
    private final int VERTEX_BYTES = VERTEX_SIZE * Float.BYTES;

    private List<Texture> textures;
    private int[] texSlots = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    private SpriteRenderer[] sprites;
    private int spriteCount;
    private boolean hasRoom;
    private float[] vertices;

    private int vaoID, vboID;
    private int maxBatchSize;
    private Shader shader;

    public RenderBatcher(int maxBatchSize) {
        shader = AssetPool.getShader("origin/assets/shaders/default.glsl");

        textures = new ArrayList<>();
        sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

        spriteCount = 0;
        hasRoom = true;
    }

    public RenderBatcher start() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_BYTES, TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_BYTES, TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);

        return this;
    }

    public void addSprite(SpriteRenderer sprite) {
        int index = spriteCount;
        sprites[index] = sprite;
        spriteCount++;

        if (sprite.getTexture() != null) {
            if (!textures.contains(sprite.getTexture())) textures.add(sprite.getTexture());
        }

        loadVertexProperties(index);

        if (spriteCount >= maxBatchSize) hasRoom = false;
    }

    public void render() {
        boolean rebuffer = false;
        for (int i = 0; i < spriteCount; ++i) {
            if (sprites[i].isDynamic()) {
                if (((DynamicSpriteRenderer) sprites[i]).isDirty()) {
                    loadVertexProperties(i);
                    ((DynamicSpriteRenderer) sprites[i]).setDirty(false);
                    rebuffer = true;
                }
            }
        }

        if (rebuffer) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        shader.use();
        shader.uploadMat4f("uProjection", Window.get().getScene().getCamera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.get().getScene().getCamera().getViewMatrix());

        for (int i = 0; i < textures.size(); ++i) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }
        shader.uploadIntArray("uTextures", texSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, spriteCount * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        textures.forEach(Texture::unbind);

        shader.detach();
    }

    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.sprites[index];
        int offset = index * 4 * VERTEX_SIZE;
        Vector4f color = sprite.getColor();

        Vector2f[] texCoords = sprite.getTexCoords();
        int texId = 0;

        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); ++i) {
                if (textures.get(i) == sprite.getTexture()) {
                    texId = i + 1;
                    break;
                }
            }
        }

        float xAdd = 1.0f;
        float yAdd = 1.0f;
        for (int i = 0; i < 4; ++i) {
            if (i == 1) yAdd = 0.0f;
            else if (i == 2) xAdd = 0.0f;
            else if (i == 3) yAdd = 1.0f;

            Transform transform = sprite.getTransform();
            vertices[offset] = transform.getPosition().x + (xAdd * transform.getScale().x);
            vertices[offset + 1] = transform.getPosition().y + (yAdd * transform.getScale().y);

            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            vertices[offset + 6] = texCoords[i].x;
            vertices[offset + 7] = texCoords[i].y;
            vertices[offset + 8] = texId;

            offset += VERTEX_SIZE;
        }
    }

    private int[] generateIndices() {
        int[] elements = new int[6 * maxBatchSize];
        for (int i = 0; i < maxBatchSize; ++i) {
            loadElementIndices(elements, i);
        }

        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset;

        elements[offsetArrayIndex + 3] = offset;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public boolean hasRoom() {
        return hasRoom;
    }

    public boolean hasRoomForTextures() { return textures.size() < 8; }

    public boolean hasTexture(Texture texture) {
        return textures.contains(texture);
    }
}
