package uk.co.alynn.one.render;

import uk.co.alynn.one.Angle;
import uk.co.alynn.one.LevelGenerator;
import uk.co.alynn.one.world.Player;
import uk.co.alynn.one.world.Side;
import uk.co.alynn.one.world.World;
import uk.co.alynn.one.world.level.LevelUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer {
    private final RenderRequest _request;
    private final World _world;
    private final ShapeRenderer _shapeRenderer;
    private final boolean _includeCharacter;
    private static float _playerHeight = 25.0f;

    public WorldRenderer(World world, RenderRequest rq, boolean includeCharacter) {
        _world = world;
        _request = rq;
        _shapeRenderer = new ShapeRenderer();
        _includeCharacter = includeCharacter;
    }

    private void updatePH() {
        float targetPH = 25.0f * (_world.getPlayer().getPosition().getSide() == Side.SIDE_A ? 1.0f
                : -1.0f);
        final float PH_SPEED = 0.5f;
        _playerHeight = (1.0f - PH_SPEED) * _playerHeight + PH_SPEED * targetPH;
    }

    public void renderWorld(FXManager fxm) {
        updatePH();
        renderBackground();
        renderSegments();
        renderEffects(fxm);
        if (_includeCharacter) {
            renderCharacter();
        }
        renderObstacles();
        renderScore();
        _shapeRenderer.dispose();
    }

    private void renderEffects(FXManager fxm) {
        setUnitTransform();
        SpriteBatch batch = _request.getBatch();
        batch.begin();
        fxm.render(batch);
        batch.end();
    }

    private void renderScore() {
        BitmapFont fnt = _request.getTextureManager().getFont();
        setZeroTransform();
        SpriteBatch batch = _request.getBatch();
        batch.begin();
        fnt.draw(batch, LevelGenerator.displayScore(_request.getConstants(),
                _world.getPlayer().getScore()), 10.0f,
                Gdx.graphics.getHeight() - 10.0f);
        batch.end();
    }

    public Vector2 playerPosition() {
        Player plr = _world.getPlayer();
        return LevelUtil.position(_world.getLevel(), plr.getPosition().getT(),
                _playerHeight);
    }

    static final Color NO_TINT = Color.WHITE;

    void drawSprite(String sprite, float x, float y, float targetWidth,
            Color col) {
        drawSprite(sprite, x, y, targetWidth, col, Angle.degrees(0));
    }

    void drawSprite(String sprite, float x, float y, float targetWidth,
            Color col, Angle rot) {
        TextureRegion rg = _request.getTextureManager().getTexture(sprite);
        _request.getBatch().begin();
        _request.getBatch().setColor(col);
        int regionWidth = rg.getRegionWidth();
        int regionHeight = rg.getRegionHeight();
        float ratio = targetWidth / regionWidth;
        float targetHeight = regionHeight * ratio;
        _request.getBatch().draw(rg, x - targetWidth / 2.0f,
                y - targetHeight / 2.0f, regionWidth, regionHeight,
                targetWidth, targetHeight, ratio, ratio,
                (float) rot.getDegrees());
        _request.getBatch().end();
        // _shapeRenderer.begin(ShapeType.Rectangle);
        // _shapeRenderer.rect(x - targetWidth * 0.5f, y - targetHeight * 0.5f,
        // targetWidth, targetHeight);
        // _shapeRenderer.end();
    }

    private void renderBackground() {
        BackgroundRenderer.renderBackground(this);
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
        setRawTransformMatrix(tf);
    }

    private void setRawTransformMatrix(Matrix4 tf) {
        _request.getBatch().setTransformMatrix(tf);
        _shapeRenderer.setTransformMatrix(tf);
    }

    void setUnitTransform() {
        setTransform(new Matrix3());
    }

    void setZeroTransform() {
        setRawTransformMatrix(new Matrix4());
    }

    private void renderCharacter() {
        CharacterRenderer.renderCharacter(this);
    }

    private void renderObstacles() {
        ObstacleRenderer.renderObstacles(this);
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
