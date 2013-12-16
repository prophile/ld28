package uk.co.alynn.one.render;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public final class Effect {

    private final PooledEffect _raw;
    private final Vector2 _pos;
    private final float _scale;

    public Effect(PooledEffect raw, Vector2 pos, float scale) {
        _raw = raw;
        _pos = pos;
        _scale = scale;
    }

    public PooledEffect getRaw() {
        return _raw;
    }

    public Matrix4 getTransform() {
        Matrix4 pony = new Matrix4();
        pony.translate(_pos.x, _pos.y, 0.0f);
        pony.scale(_scale, _scale, 1.0f);
        return pony;
    }
}
