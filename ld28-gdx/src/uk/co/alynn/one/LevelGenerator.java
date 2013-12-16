package uk.co.alynn.one;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.one.world.level.BezierLevel;
import uk.co.alynn.one.world.level.Level;
import uk.co.alynn.one.world.level.MirrorLevel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public final class LevelGenerator {
    public static Level generateLevel(Constants k) {
        List<Vector2> items = new ArrayList<Vector2>();
        BezierIteration.getBezier(items, k);
        Level baseLevel = new BezierLevel(items);
        Level scaled = new MirrorLevel(baseLevel, 1.0f, 3.0f);
        return scaled;
    }

    public static ColourScheme defaultColourScheme() {
        return new ColourScheme(new Color(0xCC / 255.0f, 0x00 / 255.0f,
                0xFF / 255.0f, 1.0f), new Color(0x33 / 255.0f, 0x00 / 255.0f,
                0x66 / 255.0f, 1.0f), Color.WHITE, Color.WHITE);

    }

    public static ColourScheme nextColourScheme() {
        return new ColourScheme(new Color(0x00 / 255.0f, 0x00 / 255.0f,
                0xCC / 255.0f, 1.0f), new Color(0x00 / 255.0f, 0x00 / 255.0f,
                0x33 / 255.0f, 1.0f), Color.WHITE, Color.WHITE);
    }
}
