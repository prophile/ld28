package uk.co.alynn.one.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class World {
    private final Player _player;
    private final List<Obstacle> _obstacles;
    private final Level _level;

    public World(Level level) {
        _player = new Player();
        _obstacles = new ArrayList<Obstacle>();
        _level = level;
    }

    public Level getLevel() {
        return _level;
    }

    public Player getPlayer() {
        return _player;
    }

    public Iterator<Obstacle> obstaclesBetween(double leftBound, double rightBound) {
        if (leftBound > rightBound) {
            return new ChainedIterator<Obstacle>(obstaclesBetween(leftBound, 1.0),
                    obstaclesBetween(0.0, rightBound));
        }
        return new PositionedInRangeIterator(_obstacles.iterator(), leftBound,
                rightBound);
    }

    void attachObstacle(Obstacle n) {
        _obstacles.add(n);
    }

    public void attachAllObstacles(BufferedReader source) throws IOException {
        ObstacleGenerator gen = new ObstacleGenerator(this);
        ObstacleFileParser parser = new ObstacleFileParser(gen, source);
        parser.process();
        for (Obstacle n : _obstacles) {
            System.out.println(n);
        }
    }

    private static abstract class FilterIterator<T> implements Iterator<T> {
        private final Iterator<T> _underlying;
        private T _next;
        private boolean _initialised = false;

        abstract boolean test(T x);

        private void advance() {
            while (_underlying.hasNext()) {
                _next = _underlying.next();
                if (test(_next)) {
                    return;
                }
            }
            _next = null;
        }

        private void init() {
            if (!_initialised) {
                advance();
                _initialised = true;
            }
        }

        public FilterIterator(Iterator<T> underlying) {
            _underlying = underlying;
        }

        @Override
        public boolean hasNext() {
            init();
            return _next != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T next() {
            init();
            T bees = _next;
            advance();
            return bees;
        }
    }

    private static class PositionedInRangeIterator extends
            FilterIterator<Obstacle> {
        private final double _leftBound;
        private final double _rightBound;

        public PositionedInRangeIterator(Iterator<Obstacle> underlying,
                double leftBound, double rightBound) {
            super(underlying);
            _leftBound = leftBound;
            _rightBound = rightBound;
        }

        @Override
        public boolean test(Obstacle x) {
            double value = x.getPosition().getT();
            return value >= _leftBound && value < _rightBound;
        }
    }

    private static class ChainedIterator<T> implements Iterator<T> {
        private final Iterator<T> _left;
        private final Iterator<T> _right;

        public ChainedIterator(Iterator<T> left, Iterator<T> right) {
            _left = left;
            _right = right;
        }

        @Override
        public boolean hasNext() {
            return _right.hasNext();
        }

        @Override
        public T next() {
            if (_left.hasNext()) {
                return _left.next();
            } else {
                return _right.next();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
