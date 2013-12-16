package uk.co.alynn.one.world;

import java.io.IOException;

import com.badlogic.gdx.Gdx;

public final class ObstacleLoader {
    public static void loadObstacles(World world, String source) {
        world.removeAllObstacles();
        try {
            world.attachAllObstacles(Gdx.files.internal(
                    "data/" + source + ".txt").reader(512));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load number data.");
        }
    }
}
