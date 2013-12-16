package uk.co.alynn.one.world;

import java.io.IOException;

import com.badlogic.gdx.Gdx;

public final class ObstacleLoader {
    public static void loadObstacles(World world, String source,
            boolean avoidPlayer) {
        world.removeAllObstacles();
        try {
            world.attachAllObstacles(
                    Gdx.files.internal("data/" + source + ".txt").reader(512),
                    avoidPlayer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load number data.");
        }
    }
}
