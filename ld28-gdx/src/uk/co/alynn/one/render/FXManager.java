package uk.co.alynn.one.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FXManager {
    private final ParticleManager _poofManager;
    private final ParticleEffect _trail;

    public FXManager() {
        _poofManager = new ParticleManager("crashbastard", 0.4f);
        _trail = new ParticleEffect();
        _trail.load(Gdx.files.internal("data/trailbastard"),
                Gdx.files.internal("data"));
    }

    public void poof1(float x, float y) {
        _poofManager.getEffect(x, y);
    }

    public void moveTrail(float x, float y) {
        _trail.setPosition(x, y);
    }

    public void update() {
        _poofManager.update();
    }

    public void render(SpriteBatch batch) {
        _poofManager.render(batch);
        _trail.draw(batch, 1 / 30.0f);
    }
}
