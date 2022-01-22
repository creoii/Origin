package creoii.origin.world;

import org.joml.Vector2f;

import java.util.function.Consumer;

public class World {
    private final WorldSize size;
    private Region[][] regions;

    public World(WorldSize size) {
        this.size = size;
        regions = new Region[size.getSize()][size.getSize()];

        for (int i = 0; i < regions.length; ++i) {
            for (int j = 0; j < regions[i].length; ++j) {
                regions[i][j] = new Region(new Vector2f(i * 640, j * 640));
            }
        }
    }

    public Region[][] getRegions() { return regions; }

    public void forEachRegion(Consumer<Region> action) {
        for (Region[] region : regions) {
            for (Region value : region) {
                action.accept(value);
            }
        }
    }

    public enum WorldSize {
        SMALL(1),
        MEDIUM(3),
        LARGE(5);

        private final int size;

        WorldSize(int size) {
            this.size = size;
        }

        public int getSize() { return size; }
    }
}
