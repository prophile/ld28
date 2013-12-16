package uk.co.alynn.one.render;

import uk.co.alynn.one.ColourScheme;
import uk.co.alynn.one.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderRequest {
    private final TextureManager _textureManager;
    private final SpriteBatch _batch;
    private final Constants _constants;
    private final ColourScheme _colScheme;

    public RenderRequest(Constants k, SpriteBatch batch, TextureManager mgr,
            ColourScheme scheme) {
        _constants = k;
        _batch = batch;
        _textureManager = mgr;
        _colScheme = scheme;
    }

    public Constants getConstants() {
        return _constants;
    }

    public SpriteBatch getBatch() {
        return _batch;
    }

    public TextureManager getTextureManager() {
        return _textureManager;
    }

    public ColourScheme getColourScheme() {
        return _colScheme;
    }
}
