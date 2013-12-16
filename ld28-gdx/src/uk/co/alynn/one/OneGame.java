package uk.co.alynn.one;

import uk.co.alynn.one.gamemode.GameMode;
import uk.co.alynn.one.gamemode.GameModeReady;
import uk.co.alynn.one.input.InputHandler;
import uk.co.alynn.one.render.TextureManager;
import bloom.Bloom;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OneGame implements ApplicationListener {
    private SpriteBatch _batch;
    private Constants _constants;
    private GameMode _gameMode;
    private TextureManager _textureManager;
    private InputHandler _inputHandler;
    private Bloom _bloom;
    private final ActionQueue _actionQueue = new ActionQueue();

    @Override
    public void create() {
        _bloom = new Bloom(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                false, false, true);

        loadConstants();
        setup();
        _gameMode = new GameModeReady(_constants);
        setBloomParams();
    }

    private void setBloomParams() {
        if (_gameMode.usesBloom()) {
            _bloom.setBloomIntesity(0.8f);
        } else {
            _bloom.setBloomIntesity(0.0f);
        }
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

    @Override
    public void dispose() {
        _batch.dispose();
    }

    @Override
    public void render() {
        setBloomParams();
        _bloom.capture();
        _gameMode = _gameMode.step(_actionQueue);
        _gameMode.render(_textureManager, _batch);
        _bloom.render();
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
