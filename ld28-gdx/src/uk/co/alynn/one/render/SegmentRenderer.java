package uk.co.alynn.one.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

final class SegmentRenderer {
    private static void drawSegment(ShapeRenderer renderer, double len,
            Matrix3 transformation) {
        Vector2 left = new Vector2(0.0f, 0.0f);
        Vector2 right = new Vector2((float) len, 0.0f);
        left.mul(transformation);
        right.mul(transformation);
        renderer.line(left.x, left.y, right.x, right.y);
    }

    static void renderSegments(WorldRenderer worldRenderer) {
        int firstSegment = worldRenderer.getWorld().getPlayer().getPosition()
                .getSegmentIndex();
        int segmentRange = worldRenderer
                .getRequest()
                .getConstants()
                .getInt("segment-range", 3,
                        "Segments to draw outside of the current segment.");
        Matrix3 transformation = worldRenderer.getBaseTransform();
        worldRenderer.transformInversion(transformation);
        startSegmentRender(worldRenderer.getShapeRenderer());
        drawForwardSegments(worldRenderer, firstSegment, segmentRange,
                transformation);
        drawReverseSegments(worldRenderer, firstSegment, segmentRange,
                transformation);
        stopSegmentRender(worldRenderer.getShapeRenderer());
    }

    static void stopSegmentRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.end();
    }

    static void startSegmentRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        shapeRenderer.begin(ShapeType.Line);
    }

    static void drawReverseSegments(WorldRenderer worldRenderer,
            int firstSegment, int segmentRange, Matrix3 transformation) {
        Matrix3 reverseTransformation = new Matrix3(transformation);
        for (int i = 0; i < segmentRange; ++i) {
            int activeSegment = firstSegment - i;
            reverseTransformation.rotate(-(float) worldRenderer.getWorld()
                    .getSegment(activeSegment + 1).getAngle().getDegrees());
            reverseTransformation.translate(-(float) worldRenderer.getWorld()
                    .getSegment(activeSegment).getLength(), 0.0f);
            drawSegment(worldRenderer.getShapeRenderer(), worldRenderer
                    .getWorld().getSegment(activeSegment).getLength(),
                    reverseTransformation);
        }
    }

    static void drawForwardSegments(WorldRenderer worldRenderer,
            int firstSegment, int segmentRange, Matrix3 transformation) {
        Matrix3 forwardTransformation = new Matrix3(transformation);
        drawSegment(worldRenderer.getShapeRenderer(), worldRenderer.getWorld()
                .getSegment(firstSegment).getLength(), forwardTransformation);
        // draw segments forward
        for (int i = 0; i < segmentRange; ++i) {
            int activeSegment = firstSegment + i;
            forwardTransformation.translate((float) worldRenderer.getWorld()
                    .getSegment(activeSegment).getLength(), 0.0f);
            forwardTransformation.rotate((float) worldRenderer.getWorld()
                    .getSegment(activeSegment + 1).getAngle().getDegrees());
            drawSegment(worldRenderer.getShapeRenderer(), worldRenderer
                    .getWorld().getSegment(activeSegment + 1).getLength(),
                    forwardTransformation);
        }
    }

}
