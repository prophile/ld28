package uk.co.alynn.one.render;

import uk.co.alynn.one.world.Level;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

final class SegmentRenderer {
    static void renderSegments(WorldRenderer worldRenderer) {
        Matrix3 transformation = new Matrix3();
        transformation.translate(-240.0f, -160.0f);
        transformation.translate(new Vector2(worldRenderer.playerPosition())
                .mul(-1.0f));
        final int SEGMENTS = 3000;
        final double EPSILON = 1.0 / SEGMENTS;
        startSegmentRender(worldRenderer.getShapeRenderer());
        renderAllSegments(worldRenderer.getWorld().getLevel(),
                worldRenderer.getShapeRenderer(), EPSILON);
        stopSegmentRender(worldRenderer.getShapeRenderer());
    }

    private static void renderAllSegments(Level lvl,
            ShapeRenderer shapeRenderer, double EPSILON) {
        for (double i = 0; i < 1.0; i += EPSILON) {
            double i_ = i + EPSILON;
            Vector2 start = lvl.f(i);
            Vector2 end = lvl.f(i_);
            shapeRenderer.line(start.x, start.y, end.x, end.y);
        }
        Vector2 start_ = lvl.f(1.0 - EPSILON);
        Vector2 end_ = lvl.f(0.0);
        shapeRenderer.line(start_.x, start_.y, end_.x, end_.y);
    }

    static void stopSegmentRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.end();
    }

    static void startSegmentRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        shapeRenderer.begin(ShapeType.Line);
    }
}
