package uk.co.alynn.one;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.one.world.World;
import uk.co.alynn.one.world.level.BezierLevel;
import uk.co.alynn.one.world.level.Level;
import uk.co.alynn.one.world.level.MirrorLevel;

import com.badlogic.gdx.math.Vector2;

public final class LevelProgression {
    private final String[] _prog;
    private final Constants _constants;
    private int _position = 0;

    public LevelProgression(Constants k, String prog) {
        _prog = prog.split(";");
        System.err.println(_prog.length);
        _constants = k;
    }

    public World popWorld() {
        String name = popLevelName();
        Level rawLevel = loadLevel(name);
        World world = new World(new MirrorLevel(rawLevel, 1.0f, 3.0f));
        return world;
    }

    private Level loadLevel(String name) {
        List<Vector2> points = new ArrayList<Vector2>();
        BezierIteration.getBezier(name, points, _constants);
        return new BezierLevel(points);
    }

    private String popLevelName() {
        String chosen = _prog[_position++ % _prog.length];
        System.err.println("ENTERING LEVEL: " + chosen);
        return chosen;
    }
}
