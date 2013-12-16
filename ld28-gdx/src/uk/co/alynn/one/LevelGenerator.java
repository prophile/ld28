package uk.co.alynn.one;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.one.world.level.BezierLevel;
import uk.co.alynn.one.world.level.Level;
import uk.co.alynn.one.world.level.MirrorLevel;

import com.badlogic.gdx.math.Vector2;

public final class LevelGenerator {
    public static Level generateLevel(Constants k) {
        List<Vector2> items = new ArrayList<Vector2>();
        BezierIteration.getBezier(items, k);
        Level baseLevel = new BezierLevel(items);
        Level scaled = new MirrorLevel(baseLevel, 1.0f, 3.0f);
        return scaled;
    }
}
