package uk.co.alynn.one.world;

import com.badlogic.gdx.math.Vector2;

public class MirrorLevel implements Level {
    private final Level _lvl;

    @Override
    public Vector2 f(double x) {
        // TODO Auto-generated method stub
        return _lvl.f(1.0 - x);
    }

    @Override
    public Vector2 fDerivative(double x) {
        // TODO Auto-generated method stub
        return _lvl.fDerivative(1.0 - x).mul(-1.0f);
    }

    public MirrorLevel(Level lvl) {
        _lvl = lvl;
    }
}
