package uk.co.alynn.one.world;

public final class Position implements Comparable<Position> {
    private final int _segmentIndex;
    private final double _position;
    private final Side _side;

    public Position(int segmentIndex, double position, Side side) {
        _segmentIndex = segmentIndex;
        _position = position;
        _side = side;
    }

    @Override
    public String toString() {
        return "Pos " + _segmentIndex + ":" + _position + " " + _side;
    }

    public int getSegmentIndex() {
        return _segmentIndex;
    }

    public double getPosition() {
        return _position;
    }

    public Side getSide() {
        return _side;
    }

    @Override
    public int compareTo(Position other) {
        if (_segmentIndex < other._segmentIndex) {
            return -1;
        } else if (_segmentIndex > other._segmentIndex) {
            return 1;
        } else if (_position < other._position) {
            return -1;
        } else if (_position > other._position) {
            return 1;
        } else if (_side == Side.SIDE_A) {
            return other._side == Side.SIDE_B ? -1 : 0;
        } else {
            return other._side == Side.SIDE_B ? 0 : 1;
        }
    }
}
