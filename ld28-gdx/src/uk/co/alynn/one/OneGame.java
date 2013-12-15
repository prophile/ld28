package uk.co.alynn.one;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.one.input.InputHandler;
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
    private OrthographicCamera _camera;
    private SpriteBatch _batch;
    private Constants _constants;
    private World _world;
    private TextureManager _textureManager;
    private InputHandler _inputHandler;
    private final ActionQueue _actionQueue = new ActionQueue();

    @Override
    public void create() {
        ConstantsLoader ldr = new ConstantsLoader(Gdx.files.internal(
                "data/constants.txt").reader());
        _constants = ldr.load();

        _textureManager = new TextureManager();

        _inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(_inputHandler);

        _inputHandler.bind("62", new Runnable() {
            @Override
            public void run() {
                _actionQueue.queueFlip();
            }
        });

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera(1, h / w);
        _batch = new SpriteBatch();

        List<Segment> segs = new ArrayList<Segment>();
        int len = 30;
        for (int i = 0; i < len; ++i) {
            segs.add(new Segment(30.0, Angle.degrees(90.0 / len)));
        }
        _world = new World(segs);
        try {
            _world.attachAllNumbers(Gdx.files.internal("data/numbers.txt")
                    .reader(512));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load number data.");
        }
    }

    @Override
    public void dispose() {
        _batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        WorldRenderer renderer = new WorldRenderer(_world, new RenderRequest(
                _constants, _batch, _textureManager));
        renderer.renderWorld();
        _batch.end();

        WorldUpdater up = new WorldUpdater(_world, _constants, 1 / 30.0);
        if (_actionQueue.popFlip()) {
            up.doFlip();
        }
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
