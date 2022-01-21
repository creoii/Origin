package creoii.origin.data;

import creoii.origin.core.util.AssetPool;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static final List<AbstractDataBuilder<?>> BUILDERS = new ArrayList<>();
    public static final ItemBuilder ITEMS = new ItemBuilder();
    public static final ClassBuilder CLASSES = new ClassBuilder();

    public void loadResources() {
        AssetPool.getShader("origin/assets/shaders/default.glsl");

        AssetPool.getTexture("src/main/resources/origin/assets/textures/classes/wizard.png");
    }
}
