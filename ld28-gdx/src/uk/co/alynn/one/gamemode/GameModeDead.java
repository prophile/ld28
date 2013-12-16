package uk.co.alynn.one.gamemode;

import uk.co.alynn.one.ActionQueue;
import uk.co.alynn.one.Constants;
import uk.co.alynn.one.LevelGenerator;
import uk.co.alynn.one.render.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class GameModeDead implements GameMode {

    private final Constants _constants;
    private final int _finalScore;

    public GameModeDead(int finalScore, Constants k) {
        _constants = k;
        _finalScore = finalScore;
    }

    @Override
    public GameMode step(ActionQueue aq) {
        if (aq.popFlip()) {
            return new GameModeLive(_constants,
                    LevelGenerator.defaultLevelProgression(_constants),
                    LevelGenerator.defaultColourScheme(), 0);
        } else {
            return this;
        }
    }

    @Override
    public void render(TextureManager texman, SpriteBatch batch) {
        TextureRegion rg = texman.getTexture("gameoverscreen2");
        BitmapFont fnt = texman.getFont();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.setTransformMatrix(new Matrix4());
        batch.begin();
        batch.setColor(Color.WHITE);
        batch.draw(rg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fnt.drawMultiLine(batch,
                LevelGenerator.displayScore(_constants, _finalScore), 0.0f,
                Gdx.graphics.getHeight() * 0.6f, Gdx.graphics.getWidth(),
                HAlignment.CENTER);
        // fnt.drawWrapped(batch,
        // LevelGenerator.displayScore(_constants, _finalScore),
        // Gdx.graphics.getWidth() / 2, 100.0f, 1000.0f, HAlignment.CENTER);
        batch.end();
    }

}
