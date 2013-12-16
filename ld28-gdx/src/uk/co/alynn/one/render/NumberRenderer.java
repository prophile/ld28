package uk.co.alynn.one.render;

import java.util.Iterator;

import uk.co.alynn.one.world.LevelUtil;
import uk.co.alynn.one.world.Number;
import uk.co.alynn.one.world.Side;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

final class NumberRenderer {
    public static void renderNumbers(WorldRenderer worldRenderer) {
        Iterator<Number> numbers = worldRenderer.getWorld().numbersBetween(0.0,
                1.0);
        worldRenderer.setUnitTransform();
        while (numbers.hasNext()) {
            Number num = numbers.next();
            if (num.isPhantom()) {
                continue;
            }
            Vector2 eyes = LevelUtil.position(worldRenderer.getWorld()
                    .getLevel(), num.getPosition().getT(), num.getPosition()
                    .getSide() == Side.SIDE_A ? 25.0 : -25.0);
            worldRenderer.drawSprite("" + num.getValue(), eyes.x, eyes.y,
                    32.0f, Color.GREEN);
            // worldRenderer.getShapeRenderer().begin(ShapeType.Circle);
            // worldRenderer.getShapeRenderer().circle(eyes.x, eyes.y, 40.0f);
            // worldRenderer.getShapeRenderer().end();
        }
    }
}
