package uk.co.alynn.one.render;

import uk.co.alynn.one.world.LevelUtil;
import uk.co.alynn.one.world.Player;
import uk.co.alynn.one.world.Side;
import uk.co.alynn.one.world.World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
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
        renderNumbers();
        _shapeRenderer.dispose();
    }

    public Vector2 playerPosition() {
        Player plr = _world.getPlayer();
        double height = 25.0 * (plr.getPosition().getSide() == Side.SIDE_A ? 1.0
                : -1.0);
        return LevelUtil.position(_world.getLevel(), plr.getPosition().getT(),
                height);
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

    public RenderRequest getRequest() {
        return _request;
    }

    public World getWorld() {
        return _world;
    }

    ShapeRenderer getShapeRenderer() {
        return _shapeRenderer;
    }
}
