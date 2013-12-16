package uk.co.alynn.one.world.level;

import com.badlogic.gdx.math.Vector2;

public class CircleLevel implements Level {

    private final float _radius;

    public CircleLevel(float radius) {
        _radius = radius;
    }

    public float getRadius() {
        return _radius;
    }

    @Override
    public Vector2 f(double x) {
        return new Vector2((float) (_radius * Math.cos(x * Math.PI * 2.0)),
                (float) (_radius * Math.sin(x * Math.PI * 2.0)));
    }

    @Override
    public Vector2 fDerivative(double x) {
        return new Vector2((float) (-_radius * Math.PI * 2.0 * Math.sin(x
                * Math.PI * 2.0)),
                (float) (_radius * Math.PI * 2.0 * Math.cos(x * Math.PI * 2.0)));
    }

}
