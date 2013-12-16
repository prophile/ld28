package uk.co.alynn.one.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

final class CharacterRenderer {
    public static void renderCharacter(WorldRenderer worldRenderer) {
        worldRenderer.setUnitTransform();
        Vector2 pp = worldRenderer.playerPosition();
        worldRenderer.drawSprite("temp", pp.x, pp.y, 30.0f, Color.CYAN);
    }
}
