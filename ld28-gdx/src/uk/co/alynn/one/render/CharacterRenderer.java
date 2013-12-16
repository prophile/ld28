package uk.co.alynn.one.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

final class CharacterRenderer {
    public static void renderCharacter(WorldRenderer worldRenderer) {
        TextureRegion rg = worldRenderer.getRequest().getTextureManager()
                .getTexture("temp");
        worldRenderer.setUnitTransform();
        Vector2 pp = worldRenderer.playerPosition();
        SpriteBatch batch = worldRenderer.getRequest().getBatch();
        batch.begin();
        batch.draw(rg, pp.x, pp.y);
        batch.end();
    }
}
