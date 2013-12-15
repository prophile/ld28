package uk.co.alynn.one.render;

import java.util.Iterator;

import uk.co.alynn.one.world.Number;
import uk.co.alynn.one.world.Position;
import uk.co.alynn.one.world.Side;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;

final class NumberRenderer {
    public static void renderNumbers(WorldRenderer worldRenderer) {
        int rootIndex = worldRenderer.getWorld().getPlayer().getPosition()
                .getSegmentIndex();
        Position leftBound = new Position(rootIndex - 10, 0.0, Side.SIDE_A);
        Position rightBound = new Position(rootIndex + 11, 0.0, Side.SIDE_B);
        renderNumbersInBounds(worldRenderer, leftBound, rightBound);
    }

    private static void renderNumbersInBounds(WorldRenderer worldRenderer,
            Position leftBound, Position rightBound) {
        Iterator<Number> bees = worldRenderer.getWorld().numbersBetween(
                leftBound, rightBound);
        worldRenderer.getRequest().getBatch().begin();
        while (bees.hasNext()) {
            renderNumber(worldRenderer, bees.next());
        }
        worldRenderer.getRequest().getBatch().end();
    }

    private static void renderNumber(WorldRenderer worldRenderer, Number pony) {
        if (skipRender(pony)) {
            return;
        }
        worldRenderer.setTransform(transformForNumber(worldRenderer, pony));
        TextureRegion rg = textureForNumber(worldRenderer, pony);
        worldRenderer.getRequest().getBatch()
                .draw(rg, 0.0f, 20.0f, 64.0f, 64.0f);
    }

    private static Matrix3 transformForNumber(WorldRenderer worldRenderer,
            Number pony) {
        return worldRenderer.melon(pony.getPosition());
    }

    private static TextureRegion textureForNumber(WorldRenderer worldRenderer,
            Number pony) {
        return worldRenderer.getRequest().getTextureManager()
                .getTexture("" + pony.getValue());
    }

    private static boolean skipRender(Number pony) {
        return pony.isPhantom();
    }
}
