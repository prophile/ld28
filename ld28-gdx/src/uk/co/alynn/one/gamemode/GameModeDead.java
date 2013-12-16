package uk.co.alynn.one.gamemode;

import uk.co.alynn.one.ActionQueue;
import uk.co.alynn.one.Constants;
import uk.co.alynn.one.render.TextureManager;
import uk.co.alynn.one.world.CircleLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class GameModeDead implements GameMode {

    private final Constants _constants;

    public GameModeDead(Constants k) {
        _constants = k;
    }

    @Override
    public GameMode step(ActionQueue aq) {
        if (aq.popFlip()) {
            return new GameModeLive(_constants, new CircleLevel(1400.0f));
        } else {
            return this;
        }
    }

    @Override
    public void render(TextureManager texman, SpriteBatch batch) {
        TextureRegion rg = texman.getTexture("gameoverscreen2");
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.setTransformMatrix(new Matrix4());
        batch.begin();
        batch.draw(rg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

}
