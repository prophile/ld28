package uk.co.alynn.one.render;

import uk.co.alynn.one.world.LevelUtil;
import uk.co.alynn.one.world.Player;
import uk.co.alynn.one.world.Side;
import uk.co.alynn.one.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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

    void drawSprite(String sprite, float x, float y) {
        TextureRegion rg = _request.getTextureManager().getTexture(sprite);
        _request.getBatch().begin();
        _request.getBatch().draw(rg, x - rg.getRegionWidth() / 2.0f,
                y - rg.getRegionHeight() / 2.0f, rg.getRegionWidth() / 2.0f,
                rg.getRegionHeight() / 2.0f, rg.getRegionWidth(),
                rg.getRegionHeight(), 1.0f, 1.0f, 0.0f);
        _request.getBatch().end();
    }

    private void renderBackground() {

    }

    private Matrix4 coreEyes() {
        Matrix4 base = new Matrix4();
        float sh = Gdx.graphics.getHeight(), sw = Gdx.graphics.getWidth();
        base.translate(sw * 0.5f, sh * 0.5f, 0.0f); // move to centre of screen
        Vector2 playerThing = playerPosition();
        base.translate(-playerThing.x, -playerThing.y, 0.0f);
        return base;
    }

    void setTransform(Matrix3 bees) {
        Matrix4 pony = new Matrix4();
        pony.set(bees);
        Matrix4 tf = coreEyes().mul(pony);
        _request.getBatch().setTransformMatrix(tf);
        _shapeRenderer.setTransformMatrix(tf);
        // test code
        Vector2 pp = playerPosition();
        Vector3 death = new Vector3(pp.x, pp.y, 0.0f);
        death.mul(tf);
        System.out.println("Screen pos: " + death);
    }

    void setUnitTransform() {
        setTransform(new Matrix3());
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
