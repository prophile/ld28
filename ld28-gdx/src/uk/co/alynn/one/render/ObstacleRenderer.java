package uk.co.alynn.one.render;

import java.util.Iterator;

import uk.co.alynn.one.world.Obstacle;
import uk.co.alynn.one.world.Side;
import uk.co.alynn.one.world.level.LevelUtil;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

final class ObstacleRenderer {
    public static void renderObstacles(WorldRenderer worldRenderer) {
        Iterator<Obstacle> obstacles = worldRenderer.getWorld().obstaclesBetween(0.0,
                1.0);
        worldRenderer.setUnitTransform();
        while (obstacles.hasNext()) {
            Obstacle ob = obstacles.next();
            if (ob.isPhantom()) {
                continue;
            }
            Vector2 eyes = LevelUtil.position(worldRenderer.getWorld()
                    .getLevel(), ob.getPosition().getT(), ob.getPosition()
                    .getSide() == Side.SIDE_A ? 25.0 : -25.0);
            worldRenderer.drawSprite("" + ob.getValue(), eyes.x, eyes.y,
                    32.0f, Color.GREEN);
            // worldRenderer.getShapeRenderer().begin(ShapeType.Circle);
            // worldRenderer.getShapeRenderer().circle(eyes.x, eyes.y, 40.0f);
            // worldRenderer.getShapeRenderer().end();
        }
    }
}
