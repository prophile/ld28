package uk.co.alynn.one.world;

import com.badlogic.gdx.math.Vector2;

public class BezierLevel implements Level {
    @Override
    public Vector2 f(double x) {
        // TODO Auto-generated method stub
        // return new Vector2(1000.0f * (float) Math.cos(2.0 * Math.PI * x),
        // 1000.0f * (float) Math.sin(2.0 * Math.PI * x));
        if (x < 0.5) {
            return new Vector2((float) (x * 1000.0), 0.0f);
        } else {
            return new Vector2(500.0f, (float) (1000.0 * (x - 0.5)));
        }
    }

    @Override
    public Vector2 fDerivative(double x) {
        return LevelUtil.derivativeDefault(this, x);
    }

}
