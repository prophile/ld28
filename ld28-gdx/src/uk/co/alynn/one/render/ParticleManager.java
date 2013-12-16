package uk.co.alynn.one.render;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class ParticleManager {
    private final ParticleEffectPool _effectPool;
    private final Set<Effect> _liveEffects;
    private final float _scale;

    public ParticleManager(String effect, float pscale) {
        _effectPool = new ParticleEffectPool(loadEffect(effect), 10, 100);
        _liveEffects = new HashSet<Effect>();
        _scale = pscale;
    }

    private static ParticleEffect loadEffect(String effect) {
        ParticleEffect fx = new ParticleEffect();
        fx.load(Gdx.files.internal("data/" + effect),
                Gdx.files.internal("data"));
        return fx;
    }

    public PooledEffect getEffect(float x, float y) {
        PooledEffect effect = _effectPool.obtain();
        effect.setPosition(0.0f, 0.0f);
        Effect fx = new Effect(effect, new Vector2(x, y), _scale);
        _liveEffects.add(fx);
        return effect;
    }

    public void update() {
        Iterator<Effect> fx = _liveEffects.iterator();
        while (fx.hasNext()) {
            Effect item = fx.next();
            PooledEffect item_ = item.getRaw();
            if (item_.isComplete()) {
                item_.free();
                fx.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        Iterator<Effect> fx = _liveEffects.iterator();
        Matrix4 oldTF = new Matrix4(batch.getTransformMatrix());
        while (fx.hasNext()) {
            Effect item = fx.next();
            Matrix4 tf = new Matrix4(oldTF);
            tf.mul(item.getTransform());
            batch.setTransformMatrix(tf);
            PooledEffect raw = item.getRaw();
            raw.draw(batch, 1 / 30.0f);
        }
        batch.setTransformMatrix(oldTF);
    }
}
