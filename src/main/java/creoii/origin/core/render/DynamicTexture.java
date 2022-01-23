package creoii.origin.core.render;

import creoii.origin.core.Main;
import org.apache.commons.math3.util.FastMath;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class DynamicTexture {
    private final String path;
    private int id;
    private int width, height;
    private List<Texture> textures = new ArrayList<>();

    public DynamicTexture(String path) {
        this.path = path;
    }

    public void load(int textureWidth, int textureHeight) {
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1).put((int) FastMath.round(FastMath.sqrt(textures.size())) * textureWidth);
        IntBuffer height = BufferUtils.createIntBuffer(1).put((int) FastMath.round(FastMath.sqrt(textures.size())) * textureHeight);
        IntBuffer channels = BufferUtils.createIntBuffer(1).put(4);
        STBImage.stbi_set_flip_vertically_on_load(true);

        int bufferSize = 0;
        try {
            for (Texture texture : textures) bufferSize += Files.size(Path.of(texture.getPath()));
        } catch (IOException e) {
            Main.LOGGER.warning("Unable to get size of sub-texture of ".concat(path));
        }
        ByteBuffer image = BufferUtils.createByteBuffer(bufferSize);
        for (Texture texture : textures) {
            image.put(STBImage.stbi_load(texture.getPath(), BufferUtils.createIntBuffer(1), BufferUtils.createIntBuffer(1), BufferUtils.createIntBuffer(1), 4).flip());
        }

        this.width = width.get(0);
        this.height = height.get(0);
        if (channels.get(0) == 3) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
        } else if (channels.get(0) == 4) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        } else Main.LOGGER.info("Unknown number of channels from ".concat(path));
    }

    public String getPath() { return path; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public List<Texture> getTextures() { return textures; }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void export(String format) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File output = new File(path);
        ImageIO.write(image, format, output);
    }
}
