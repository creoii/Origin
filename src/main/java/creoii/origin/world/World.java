package creoii.origin.world;

import java.util.function.Consumer;

public class World {
    private final WorldSize size;
    private Region[][] regions;

    public World(WorldSize size) {
        this.size = size;
        regions = new Region[size.getSize()][size.getSize()];

        for (int i = 0; i < regions.length; ++i) {
            for (int j = 0; j < regions[i].length; ++j) {
                regions[i][j] = new Region();
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
        SMALL(2),
        MEDIUM(4),
        LARGE(6);

        private final int size;

        WorldSize(int size) {
            this.size = size;
        }

        public int getSize() { return size; }
    }
}
