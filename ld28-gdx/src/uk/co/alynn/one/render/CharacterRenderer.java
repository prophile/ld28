package uk.co.alynn.one.render;

import uk.co.alynn.one.ColourScheme;

import com.badlogic.gdx.math.Vector2;

final class CharacterRenderer {
    public static void renderCharacter(WorldRenderer worldRenderer) {
        worldRenderer.setUnitTransform();
        Vector2 pp = worldRenderer.playerPosition();
        ColourScheme scheme = worldRenderer.getRequest().getColourScheme();
        final float HACK_IN_OFF_X = -26.5f, HACK_IN_OFF_Y = -27f;
        final float HACK_OUT_OFF_X = -28.0f, HACK_OUT_OFF_Y = -28.0f;
        worldRenderer.drawSprite("Player_1_outside_128x128", pp.x
                + HACK_OUT_OFF_X, pp.y + HACK_OUT_OFF_Y, 86.0f,
                scheme.getTrack());
        worldRenderer.drawSprite("Player_1_inside_128x128", pp.x
                + HACK_IN_OFF_X, pp.y + HACK_IN_OFF_Y, 72.0f,
                scheme.getForeground());
    }
}
