package uk.co.alynn.one.render;

import uk.co.alynn.one.world.World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer {
    private final RenderRequest _request;
    private final World _world;
    private final ShapeRenderer _shapeRenderer;

    public WorldRenderer(World world, RenderRequest rq) {
        _world = world;
        _request = rq;
        _shapeRenderer = new ShapeRenderer();
    }

    public void renderWorld() {
        renderBackground();
        renderSegments();
        _shapeRenderer.dispose();
    }

    private void renderBackground() {

    }

    private void drawSegment(double len, Matrix3 transformation) {
        Vector2 left = new Vector2(0.0f, 0.0f);
        Vector2 right = new Vector2((float) len, 0.0f);
        left.mul(transformation);
        right.mul(transformation);
        System.out.println(left + ":" + right);
        _shapeRenderer.line(left.x, left.y, right.x, right.y);
    }

    private void renderSegments() {
        int firstSegment = _world.getPlayer().getPosition().getSegmentIndex();
        int segmentRange = _request.getConstants().getInt("segment-range", 3,
                "Segments to draw outside of the current segment.");
        int lowerSegmentBound = firstSegment - segmentRange, upperSegmentBound = firstSegment
                + segmentRange;
        Matrix3 transformation = new Matrix3().translate(240.0f, 160.0f);
        _shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        _shapeRenderer.begin(ShapeType.Line);
        float d = (float) _world.getPlayer().getPosition().getPosition();
        Matrix3 forwardTransformation = new Matrix3(transformation);
        forwardTransformation.translate(-d, 0.0f);
        drawSegment(_world.getSegment(firstSegment).getLength(),
                forwardTransformation);
        // draw segments forward
        for (int i = 0; i < segmentRange; ++i) {
            int activeSegment = firstSegment + i;
            forwardTransformation.translate(
                    (float) _world.getSegment(activeSegment).getLength(), 0.0f);
            forwardTransformation.rotate((float) _world.getSegment(
                    activeSegment + 1).getAngle());
            drawSegment(_world.getSegment(activeSegment + 1).getLength(),
                    forwardTransformation);
        }
        _shapeRenderer.end();
    }
}
