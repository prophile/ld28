package uk.co.alynn.one.world.level;

import com.badlogic.gdx.math.Vector2;

public final class LevelUtil {
    public static Vector2 position(Level lvl, double x, double height) {
        Vector2 base = lvl.f(x);
        Vector2 deriv = lvl.fDerivative(x);
        deriv.div(deriv.len());
        return new Vector2((float) (base.x + height * deriv.y),
                (float) (base.y - height * deriv.x));
    }

    public static Vector2 derivativeDefault(Level lvl, double x) {
        final double EPSILON = 0.001;
        Vector2 left = lvl.f(x - EPSILON);
        Vector2 right = lvl.f(x + EPSILON);
        return new Vector2((float) ((right.x - left.x) / (2.0 * EPSILON)),
                (float) ((right.y - left.y) / (2.0 * EPSILON)));
    }
}
