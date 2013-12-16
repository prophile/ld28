package uk.co.alynn.one.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FXManager {
    private final ParticleManager _poofManager;

    public FXManager() {
        _poofManager = new ParticleManager("crashbastard", 0.4f);
    }

    public void poof1(float x, float y) {
        _poofManager.getEffect(x, y);
    }

    public void update() {
        _poofManager.update();
    }

    public void render(SpriteBatch batch) {
        _poofManager.render(batch);
    }
}
