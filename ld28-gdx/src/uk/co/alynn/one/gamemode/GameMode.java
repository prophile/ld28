package uk.co.alynn.one.gamemode;

import uk.co.alynn.one.ActionQueue;
import uk.co.alynn.one.render.TextureManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameMode {
    public GameMode step(ActionQueue aq);

    public void render(TextureManager texman, SpriteBatch batch);

    public boolean usesBloom();
}
