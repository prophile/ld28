package uk.co.alynn.one.render;

import uk.co.alynn.one.world.Position;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

final class CharacterRenderer {
    public static void renderCharacter(WorldRenderer worldRenderer) {
        TextureRegion rg = worldRenderer.getRequest().getTextureManager()
                .getTexture("temp");
        Position playerPosition = worldRenderer.getWorld().getPlayer()
                .getPosition();
        worldRenderer.setTransform(worldRenderer.melon(playerPosition));
        worldRenderer.getRequest().getBatch().begin();
        worldRenderer.getRequest().getBatch().draw(rg, 0.0f, 20.0f);
        worldRenderer.getRequest().getBatch().end();
    }
}
