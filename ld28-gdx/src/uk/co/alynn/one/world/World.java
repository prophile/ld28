package uk.co.alynn.one.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.alynn.one.world.level.Level;

public final class World {
    private final Player _player;
    private final List<Obstacle> _obstacles;
    private final Level _level;
    private final String _numbersGoo;

    public World(Level level, String numbersGoo) {
        _player = new Player();
        _obstacles = new ArrayList<Obstacle>();
        _level = level;
        _numbersGoo = numbersGoo;
    }

    public String getNumbersSet() {
        return _numbersGoo;
    }

    public Level getLevel() {
        return _level;
    }

    public Player getPlayer() {
        return _player;
    }

    public Iterator<Obstacle> obstaclesBetween(double leftBound,
            double rightBound) {
        if (leftBound > rightBound) {
            return new ChainedIterator<Obstacle>(obstaclesBetween(leftBound,
                    1.0), obstaclesBetween(0.0, rightBound));
        }
        return new PositionedInRangeIterator(_obstacles.iterator(), leftBound,
                rightBound);
    }

    void attachObstacle(Obstacle n) {
        _obstacles.add(n);
    }

    void removeAllObstacles() {
        _obstacles.clear();
    }

    void attachAllObstacles(BufferedReader source, boolean avoidPlayer)
            throws IOException {
        ObstacleGenerator gen = new ObstacleGenerator(this, avoidPlayer);
        ObstacleFileParser parser = new ObstacleFileParser(gen, source);
        parser.process();
        for (Obstacle n : _obstacles) {
            System.out.println(n);
        }
    }
}
