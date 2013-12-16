package uk.co.alynn.one;

import com.badlogic.gdx.graphics.Color;

public final class ColourScheme {
    private final Color _foreground;
    private final Color _background;
    private final Color _track;
    private final Color _bad;

    public ColourScheme(Color fg, Color bg, Color tr, Color bd) {
        _foreground = fg;
        _background = bg;
        _track = tr;
        _bad = bd;
    }

    public Color getBad() {
        return _bad;
    }

    public Color getTrack() {
        return _track;
    }

    public Color getBackground() {
        return _background;
    }

    public Color getForeground() {
        return _foreground;
    }
}
