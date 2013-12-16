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
}
