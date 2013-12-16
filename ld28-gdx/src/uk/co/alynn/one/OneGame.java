package uk.co.alynn.one;

import uk.co.alynn.one.gamemode.GameMode;
import uk.co.alynn.one.gamemode.GameModeLive;
import uk.co.alynn.one.input.InputHandler;
import uk.co.alynn.one.render.TextureManager;
import uk.co.alynn.one.world.CircleLevel;
import uk.co.alynn.one.world.Level;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OneGame implements ApplicationListener {
    private SpriteBatch _batch;
    private Constants _constants;
    private GameMode _gameMode;
    private TextureManager _textureManager;
    private InputHandler _inputHandler;
    private final ActionQueue _actionQueue = new ActionQueue();

    @Override
    public void create() {
        loadConstants();
        setup();
        _gameMode = new GameModeLive(_constants, generateLevel());
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

    private Level generateLevel() {
        // return new MirrorLevel(new CircleLevel(1000.0f));
        return new CircleLevel(1000.0f);
    }

    @Override
    public void dispose() {
        _batch.dispose();
    }

    @Override
    public void render() {
        _gameMode = _gameMode.step(_actionQueue);
        _gameMode.render(_textureManager, _batch);
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
