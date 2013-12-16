package uk.co.alynn.one.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FXManager {
    private final ParticleManager _poofManager;
    private final ParticleEffect _trail;
    private final ParticleManager _goatManager;
    private static final int FX_GOAT = 0x01;
    private static final int FX_TRAIL = 0x02;
    private static final int FX_POOF = 0x04;
    private static final int FX = -1;

    public FXManager() {
        _poofManager = new ParticleManager("crashbastard", 0.4f);
        _goatManager = new ParticleManager("goatbastard", 1.0f);
        _trail = new ParticleEffect();
        _trail.load(Gdx.files.internal("data/trailbastard3"),
                Gdx.files.internal("data"));
    }

    public void poof1(float x, float y) {
        _poofManager.getEffect(x, y);
    }

    public void goat(float x, float y) {
        _goatManager.getEffect(x, y);
    }

    public void moveTrail(float x, float y) {
        _trail.setPosition(x, y);
    }

    public void update() {
        _poofManager.update();
        _goatManager.update();
    }

    public void render(SpriteBatch batch) {
        if ((FX & FX_TRAIL) != 0) {
            _trail.draw(batch, 1 / 30.0f);
        }
        if ((FX & FX_POOF) != 0) {
            _poofManager.render(batch);
        }
        if ((FX & FX_GOAT) != 0) {
            _goatManager.render(batch);
        }
    }
}
