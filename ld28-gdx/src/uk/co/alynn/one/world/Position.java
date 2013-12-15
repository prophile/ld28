package uk.co.alynn.one.world;

public final class Position implements Comparable<Position> {
    private final double _t;
    private final Side _side;

    public Position(double t, Side side) {
        _t = (t + 10000.0) % 1.0;
        _side = side;
    }

    @Override
    public String toString() {
        return "Pos " + _t + " " + _side;
    }

    public double getT() {
        return _t;
    }

    public Side getSide() {
        return _side;
    }

    @Override
    public int compareTo(Position other) {
        if (_t < other._t) {
            return -1;
        } else if (_t > other._t) {
            return 1;
        } else if (_side == Side.SIDE_A) {
            return other._side == Side.SIDE_B ? -1 : 0;
        } else {
            return other._side == Side.SIDE_B ? 0 : 1;
        }
    }

    public double distanceTo(Position other) {
        double absDist = Math.abs(_t - other._t);
        return Math.min(absDist, 1 - absDist);
    }
}
