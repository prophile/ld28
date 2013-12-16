package uk.co.alynn.one;

import java.io.IOException;

import uk.co.alynn.one.input.InputHandler;
import uk.co.alynn.one.render.RenderRequest;
import uk.co.alynn.one.render.TextureManager;
import uk.co.alynn.one.render.WorldRenderer;
import uk.co.alynn.one.world.CircleLevel;
import uk.co.alynn.one.world.GameOverException;
import uk.co.alynn.one.world.Level;
import uk.co.alynn.one.world.MirrorLevel;
import uk.co.alynn.one.world.World;
import uk.co.alynn.one.world.WorldUpdater;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class OneGame implements ApplicationListener {
    private SpriteBatch _batch;
    private Constants _constants;
    private World _world;
    private TextureManager _textureManager;
    private InputHandler _inputHandler;
    private final ActionQueue _actionQueue = new ActionQueue();

    @Override
    public void create() {
        loadConstants();
        setup();
        generateWorld();
    }

    private void setup() {
        setupTextureManager();
        setupInput();
        setupGraphics();
    }

    private void setupGraphics() {
        setupSpriteBatch();
    }

    private void setupSpriteBatch() {
        _batch = new SpriteBatch();
    }

    private void setupTextureManager() {
        _textureManager = new TextureManager();
    }

    private void loadConstants() {
        ConstantsLoader ldr = new ConstantsLoader(Gdx.files.internal(
                "data/constants.txt").reader());
        _constants = ldr.load();
    }

    private void setupInput() {
        _inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(_inputHandler);

        bindKeys();
    }

    private void bindKeys() {
        bindFlipKey();
    }

    private void bindFlipKey() {
        _inputHandler.bind("62", new Runnable() {
            @Override
            public void run() {
                _actionQueue.queueFlip();
            }
        });
    }

    private void generateWorld() {
        createWorldObject();
        loadWorldNumbers();
    }

    private void createWorldObject() {
        _world = new World(generateLevel());
    }

    private void loadWorldNumbers() {
        try {
            _world.attachAllNumbers(Gdx.files.internal("data/numbers.txt")
                    .reader(512));
        } catch (IOException e) {
            handleNumberLoadError(e);
        }
    }

    private void handleNumberLoadError(IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to load number data.");
    }

    private Level generateLevel() {
        return new MirrorLevel(new CircleLevel(1000.0f));
    }

    @Override
    public void dispose() {
        _batch.dispose();
    }

    @Override
    public void render() {
        clearScreen();
        drawWorld();
        updateWorld();
    }

    private void drawWorld() {
        _batch.setTransformMatrix(new Matrix4());
        WorldRenderer renderer = new WorldRenderer(_world, new RenderRequest(
                _constants, _batch, _textureManager));
        renderer.renderWorld();
    }

    private void updateWorld() {
        WorldUpdater up = new WorldUpdater(_world, _constants, 1 / 30.0);
        emptyActionQueue(up);
        runOneTick(up);
    }

    private void runOneTick(WorldUpdater up) {
        try {
            up.tick();
        } catch (GameOverException go) {
            // regenerate world
            generateWorld();
            System.err.println("GAME OVER MAN");
        }
    }

    private void emptyActionQueue(WorldUpdater up) {
        if (_actionQueue.popFlip()) {
            up.doFlip();
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
