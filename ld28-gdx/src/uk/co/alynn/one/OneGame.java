package uk.co.alynn.one;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.one.render.RenderRequest;
import uk.co.alynn.one.render.TextureManager;
import uk.co.alynn.one.render.WorldRenderer;
import uk.co.alynn.one.world.Segment;
import uk.co.alynn.one.world.World;
import uk.co.alynn.one.world.WorldUpdater;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OneGame implements ApplicationListener {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Constants constants;
    private World world;
    private TextureManager textureManager;

    @Override
    public void create() {
        ConstantsLoader ldr = new ConstantsLoader(Gdx.files.internal(
                "data/constants.txt").reader());
        constants = ldr.load();

        textureManager = new TextureManager();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(1, h / w);
        batch = new SpriteBatch();

        List<Segment> segs = new ArrayList<Segment>();
        int len = 30;
        for (int i = 0; i < len; ++i) {
            segs.add(new Segment(30.0, 90.0 / len));
        }
        world = new World(segs);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        WorldRenderer renderer = new WorldRenderer(world, new RenderRequest(
                constants, batch, textureManager));
        renderer.renderWorld();
        batch.end();

        WorldUpdater up = new WorldUpdater(world, constants, 1 / 30.0);
        up.tick();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
