package uk.co.alynn.one.render;

import uk.co.alynn.one.world.Position;
import uk.co.alynn.one.world.Side;
import uk.co.alynn.one.world.World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

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
        renderNumbers();
        _shapeRenderer.dispose();
    }

    private void renderBackground() {

    }

    void setTransform(Matrix3 bees) {
        Matrix4 pony = new Matrix4();
        pony.set(bees);
        _request.getBatch().setTransformMatrix(pony);
    }

    private void renderCharacter() {
        CharacterRenderer.renderCharacter(this);
    }

    private void renderNumbers() {
        NumberRenderer.renderNumbers(this);
    }

    private void renderSegments() {
        SegmentRenderer.renderSegments(this);
    }

    Matrix3 getBaseTransform() {
        Matrix3 transformation = new Matrix3().translate(240.0f, 160.0f);
        float d = (float) _world.getPlayer().getPosition().getPosition();
        transformation.translate(-d, 0.0f);
        return transformation;
    }

    void transformInversion(Matrix3 transformation) {
        if (_world.getPlayer().getPosition().getSide() == Side.SIDE_B) {
            transformation.scale(1.0f, -1.0f);
        }
    }

    public RenderRequest getRequest() {
        return _request;
    }

    public World getWorld() {
        return _world;
    }

    Matrix3 melon(Position pos) {
        Matrix3 base = getBaseTransform();
        Position playerPosition = _world.getPlayer().getPosition();
        int targetSegment = pos.getSegmentIndex();
        int referenceSegment = playerPosition.getSegmentIndex();
        if (targetSegment > referenceSegment) {
            for (int i = targetSegment; i < referenceSegment; ++i) {
                base.translate((float) _world.getSegment(i).getLength(), 0.0f);
                base.rotate((float) _world.getSegment(i).getAngle()
                        .getDegrees());
            }
        } else if (targetSegment < referenceSegment) {
            for (int i = referenceSegment; i > targetSegment; --i) {
                base.rotate(-(float) _world.getSegment(i + 1).getAngle()
                        .getDegrees());
                base.translate(-(float) _world.getSegment(i).getLength(), 0.0f);
            }
        }
        if (pos.getSide() != _world.getPlayer().getPosition().getSide()) {
            base.scale(1.0f, -1.0f);
        }
        base.translate((float) pos.getPosition(), 0.0f);
        return base;
    }

    ShapeRenderer getShapeRenderer() {
        return _shapeRenderer;
    }
}
