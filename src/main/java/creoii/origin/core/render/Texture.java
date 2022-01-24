package creoii.origin.core.render;

import creoii.origin.core.Main;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private final String path;
    private final int id;
    private int width, height;
    private final ByteBuffer image;
    private BufferedImage bufferedImage;

    public Texture(String path) {
        this.path = path;

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        STBImage.stbi_set_flip_vertically_on_load(true);
        image = STBImage.stbi_load(path, width, height, channels, 0);

        if (image != null) {
            this.width = width.get(0);
            this.height = height.get(0);
            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else Main.LOGGER.info("Unknown number of channels from ".concat(path));
            STBImage.stbi_image_free(image);
        } else {
            Main.LOGGER.info("Unable to load texture from ".concat(path));
        }
    }

    public String getPath() { return path; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public ByteBuffer getImage() { return image; }

    public BufferedImage toBufferedImage() {
        if (bufferedImage == null) {
            if (image.hasArray()) {
                ByteArrayInputStream bytes = new ByteArrayInputStream(image.array());
                try {
                    bufferedImage = ImageIO.read(bytes);
                } catch (IOException e) {
                    Main.LOGGER.warning("Unable to convert texture ".concat(path).concat(" to an image."));
                }
            } else return null;
        }

        return bufferedImage;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
