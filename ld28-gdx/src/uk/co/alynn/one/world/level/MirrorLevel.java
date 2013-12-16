package uk.co.alynn.one.world.level;

import com.badlogic.gdx.math.Vector2;

public class MirrorLevel implements Level {
    private final Level _lvl;
    private final double _sourceFactor;
    private final double _scaleFactor;

    @Override
    public Vector2 f(double x) {
        // TODO Auto-generated method stub
        return _lvl.f(x * _sourceFactor % 1.0).mul((float) _scaleFactor);
    }

    @Override
    public Vector2 fDerivative(double x) {
        // TODO Auto-generated method stub
        return _lvl.fDerivative(x * _sourceFactor % 1.0).mul(
                (float) (_sourceFactor * _scaleFactor));
    }

    public MirrorLevel(Level lvl, float sourceFactor, float scaleFactor) {
        _lvl = lvl;
        _sourceFactor = sourceFactor;
        _scaleFactor = scaleFactor;
    }
}
