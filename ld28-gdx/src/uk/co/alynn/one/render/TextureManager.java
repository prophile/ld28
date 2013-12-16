package uk.co.alynn.one.render;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

final public class TextureManager {
    private final Map<String, TextureRegion> _regionCache;
    private BitmapFont _fontCache = null;

    public TextureManager() {
        _regionCache = new HashMap<String, TextureRegion>();
    }

    public TextureRegion getTexture(String name) {
        TextureRegion cachedRegion = _regionCache.get(name);
        if (cachedRegion == null) {
            cachedRegion = loadTexture(name);
            _regionCache.put(name, cachedRegion);
        }
        return cachedRegion;
    }

    private TextureRegion loadTexture(String name) {
        // implementation 1: just load from a file
        String filename = "data/" + name + ".png";
        FileHandle fh = Gdx.files.internal(filename);
        Texture tx = new Texture(fh);
        return new TextureRegion(tx);
    }

    public BitmapFont getFont() {
        if (_fontCache == null) {
            _fontCache = new BitmapFont(Gdx.files.internal("data/calibri.fnt"),
                    Gdx.files.internal("data/calibri.png"), false);
        }
        return _fontCache;
    }
}
