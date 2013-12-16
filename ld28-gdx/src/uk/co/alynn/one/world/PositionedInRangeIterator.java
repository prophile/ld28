package uk.co.alynn.one.world;

import java.util.Iterator;

class PositionedInRangeIterator extends
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