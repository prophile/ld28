package uk.co.alynn.one.render;

import uk.co.alynn.one.world.Side;
import uk.co.alynn.one.world.World;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
        renderCharacter();
        _shapeRenderer.dispose();
    }

    private void renderBackground() {

    }

    private void renderCharacter() {
        Sprite sprite = new Sprite(_request.getTextureManager().getTexture(
                "temp"));
        sprite.setPosition(220.0f, 160.0f);
        sprite.setOrigin(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
        sprite.draw(_request.getBatch());
    }

    private void drawSegment(double len, Matrix3 transformation) {
        Vector2 left = new Vector2(0.0f, 0.0f);
        Vector2 right = new Vector2((float) len, 0.0f);
        left.mul(transformation);
        right.mul(transformation);
        _shapeRenderer.line(left.x, left.y, right.x, right.y);
    }

    private void renderSegments() {
        int firstSegment = _world.getPlayer().getPosition().getSegmentIndex();
        int segmentRange = _request.getConstants().getInt("segment-range", 3,
                "Segments to draw outside of the current segment.");
        Matrix3 transformation = getBaseTransform();
        startSegmentRender();
        drawForwardSegments(firstSegment, segmentRange, transformation);
        drawReverseSegments(firstSegment, segmentRange, transformation);
        stopSegmentRender();
    }

    private void stopSegmentRender() {
        _shapeRenderer.end();
    }

    private void startSegmentRender() {
        _shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        _shapeRenderer.begin(ShapeType.Line);
    }

    private void drawReverseSegments(int firstSegment, int segmentRange,
            Matrix3 transformation) {
        Matrix3 reverseTransformation = new Matrix3(transformation);
        for (int i = 0; i < segmentRange; ++i) {
            int activeSegment = firstSegment - i;
            reverseTransformation.rotate(-(float) _world
                    .getSegment(activeSegment + 1).getAngle().getDegrees());
            reverseTransformation
                    .translate(-(float) _world.getSegment(activeSegment)
                            .getLength(), 0.0f);
            drawSegment(_world.getSegment(activeSegment).getLength(),
                    reverseTransformation);
        }
    }

    private void drawForwardSegments(int firstSegment, int segmentRange,
            Matrix3 transformation) {
        Matrix3 forwardTransformation = new Matrix3(transformation);
        drawSegment(_world.getSegment(firstSegment).getLength(),
                forwardTransformation);
        // draw segments forward
        for (int i = 0; i < segmentRange; ++i) {
            int activeSegment = firstSegment + i;
            forwardTransformation.translate(
                    (float) _world.getSegment(activeSegment).getLength(), 0.0f);
            forwardTransformation.rotate((float) _world
                    .getSegment(activeSegment + 1).getAngle().getDegrees());
            drawSegment(_world.getSegment(activeSegment + 1).getLength(),
                    forwardTransformation);
        }
    }

    private Matrix3 getBaseTransform() {
        Matrix3 transformation = new Matrix3().translate(240.0f, 160.0f);
        float d = (float) _world.getPlayer().getPosition().getPosition();
        transformation.translate(-d, 0.0f);
        transformInversion(transformation);
        return transformation;
    }

    private void transformInversion(Matrix3 transformation) {
        if (_world.getPlayer().getPosition().getSide() == Side.SIDE_B) {
            transformation.scale(1.0f, -1.0f);
        }
    }
}
