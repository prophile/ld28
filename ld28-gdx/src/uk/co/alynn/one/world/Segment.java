package uk.co.alynn.one.world;

public final class Segment {
    private final double _angle;
    private final double _length;

    public Segment(double len, double angle) {
        _angle = angle;
        _length = len;
    }

    public double getAngle() {
        return _angle;
    }

    public double getLength() {
        return _length;
    }
}
