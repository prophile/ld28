package uk.co.alynn.one.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class World {
    private final Player _player;
    private final List<Number> _numbers;
    private final Level _level;

    public World(Level level) {
        _player = new Player();
        _numbers = new ArrayList<Number>();
        _level = level;
    }

    public Level getLevel() {
        return _level;
    }

    public Player getPlayer() {
        return _player;
    }

    public Iterator<Number> numbersBetween(double leftBound, double rightBound) {
        if (leftBound > rightBound) {
            return new ChainedIterator<Number>(numbersBetween(leftBound, 1.0),
                    numbersBetween(0.0, rightBound));
        }
        return new PositionedInRangeIterator(_numbers.iterator(), leftBound,
                rightBound);
    }

    void attachNumber(Number n) {
        _numbers.add(n);
    }

    public void attachAllNumbers(BufferedReader source) throws IOException {
        NumberGenerator gen = new NumberGenerator(this);
        NumberFileParser parser = new NumberFileParser(gen, source);
        parser.process();
        for (Number n : _numbers) {
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
            FilterIterator<Number> {
        private final double _leftBound;
        private final double _rightBound;

        public PositionedInRangeIterator(Iterator<Number> underlying,
                double leftBound, double rightBound) {
            super(underlying);
            _leftBound = leftBound;
            _rightBound = rightBound;
        }

        @Override
        public boolean test(Number x) {
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
