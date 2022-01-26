package creoii.origin.data;

import creoii.origin.core.render.Texture;
import creoii.origin.core.render.sprite.Spritesheet;
import creoii.origin.core.util.AssetPool;
import creoii.origin.core.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static final List<DataBuilder<?>> BUILDERS = new ArrayList<>();
    public static final ItemBuilder ITEMS = new ItemBuilder();
    public static final ClassBuilder CLASSES = new ClassBuilder();
    public static final TileBuilder TILES = new TileBuilder();

    public void loadResources() {
        long startTime = System.nanoTime();
        AssetPool.getShader("origin/assets/shaders/default.glsl");

        FileUtil.forEachFileName("origin/assets/textures", name -> {
            FileUtil.forEachFileName("origin/assets/textures/".concat(name), name1 -> {
                AssetPool.getTexture("src/main/resources/origin/assets/textures/".concat(name).concat("/").concat(name1));
            });
        });

        Spritesheet x8 = new Spritesheet("src/main/resources/origin/assets/spritesheets/sprites_8x8.png", 8, 8);
        Spritesheet x16 = new Spritesheet("src/main/resources/origin/assets/spritesheets/sprites_16x16.png", 16, 16);
        for (Texture texture : AssetPool.TEXTURES.values()){
            // 8x8 textures
            if (texture.getWidth() == 8 && texture.getHeight() == 8) {
                x8.addTexture(texture);
            }
            // 16x16 textures
            else if (texture.getWidth() == 16 && texture.getHeight() == 16) {
                x16.addTexture(texture);
            }
        }
        x8.load();
        x16.load();
        AssetPool.addSpritesheet(x8.getPath(), x8);
        AssetPool.addSpritesheet(x16.getPath(), x16);

        long endTime = System.nanoTime();
        System.out.println("Successfully loaded resources in ".concat(String.valueOf((endTime - startTime) / 1000000d)).concat(" ms"));
    }
}
