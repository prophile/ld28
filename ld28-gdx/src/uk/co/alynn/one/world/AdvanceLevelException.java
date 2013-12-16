package uk.co.alynn.one.world;

import uk.co.alynn.one.world.level.Level;

public class AdvanceLevelException extends Exception {
    private final Level _level;

    public AdvanceLevelException(Level level) {
        _level = level;
    }

    public Level getLevel() {
        return _level;
    }

    private static final long serialVersionUID = -22253818894436237L;

}
