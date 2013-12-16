package uk.co.alynn.one.render;

import uk.co.alynn.one.ColourScheme;
import uk.co.alynn.one.world.level.Level;
import uk.co.alynn.one.world.level.LevelUtil;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

final class SegmentRenderer {
    static void renderSegments(WorldRenderer worldRenderer) {
        final int SEGMENTS = 4000;
        final double EPSILON = 1.0 / SEGMENTS;
        worldRenderer.setUnitTransform();
        startSegmentRender(worldRenderer.getRequest().getColourScheme(),
                worldRenderer.getShapeRenderer());
        renderAllSegments(
                worldRenderer.getWorld().getLevel(),
                worldRenderer.getShapeRenderer(),
                EPSILON,
                worldRenderer
                        .getRequest()
                        .getConstants()
                        .getDouble("line-width", 5.0,
                                "Pixel width of the line."));
        stopSegmentRender(worldRenderer.getShapeRenderer());
    }

    private static void renderSegment(Level lvl, ShapeRenderer shapeRenderer,
            double width, double a, double b) {
        Vector2 p = LevelUtil.position(lvl, a, width * 0.5);
        Vector2 q = LevelUtil.position(lvl, a, width * -0.5);
        Vector2 r = LevelUtil.position(lvl, b, width * 0.5);
        Vector2 s = LevelUtil.position(lvl, b, width * -0.5);
        shapeRenderer.filledTriangle(p.x, p.y, q.x, q.y, r.x, r.y);
        shapeRenderer.filledTriangle(q.x, q.y, s.x, s.y, r.x, r.y);
    }

    private static void renderAllSegments(Level lvl,
            ShapeRenderer shapeRenderer, double EPSILON, double width) {
        for (double i = 0; i < 1.0; i += EPSILON) {
            double i_ = i + EPSILON;
            renderSegment(lvl, shapeRenderer, width, i, i_);
        }
        renderSegment(lvl, shapeRenderer, width, 1.0 - EPSILON, 0.0);
    }

    static void stopSegmentRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.end();
    }

    static void startSegmentRender(ColourScheme scheme,
            ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(scheme.getTrack());
        shapeRenderer.begin(ShapeType.FilledTriangle);
    }
}
