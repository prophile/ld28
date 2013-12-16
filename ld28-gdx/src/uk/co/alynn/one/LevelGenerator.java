package uk.co.alynn.one;

import uk.co.alynn.one.world.level.CircleLevel;
import uk.co.alynn.one.world.level.Level;

public final class LevelGenerator {
    public static Level generateLevel(Constants k) {
        return new CircleLevel(1000.0f);
    }
}
