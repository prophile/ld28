package uk.co.alynn.one.world.level;

import com.badlogic.gdx.math.Vector2;

public interface Level {
    Vector2 f(double x);

    Vector2 fDerivative(double x);
}
