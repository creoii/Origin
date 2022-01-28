package creoii.origin.world;

import creoii.origin.bullet.Bullet;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public class World {
    private final WorldSize size;
    private final Region[][] regions;
    private final ConcurrentMap<Integer, Bullet> bullets;

    public World(WorldSize size) {
        this.size = size;
        regions = new Region[size.getSize()][size.getSize()];
        bullets = new ConcurrentHashMap<Integer, Bullet>();

        for (int i = 0; i < regions.length; ++i) {
            for (int j = 0; j < regions[i].length; ++j) {
                regions[i][j] = new Region(new Vector2f(i * 640, j * 640));
            }
        }
    }

    public WorldSize getSize() { return size; }
    public Region[][] getRegions() { return regions; }

    public void forEachRegion(Consumer<Region> action) {
        for (Region[] region : regions) {
            for (Region value : region) {
                action.accept(value);
            }
        }
    }

    public Collection<Bullet> getBullets() { return bullets.values(); }
    public void addBullet(Bullet bullet) { bullets.put(bullets.size(), bullet); }
    public void removeBullet(Bullet bullet) { bullets.remove(bullet.getId()); }

    public void update(float deltaTime) {
        bullets.forEach((id, bullet) -> bullet.update(deltaTime));
    }

    public enum WorldSize {
        SMALL(1),
        MEDIUM(2),
        LARGE(3);

        private final int size;

        WorldSize(int size) {
            this.size = size;
        }

        public int getSize() { return size; }
    }
}
