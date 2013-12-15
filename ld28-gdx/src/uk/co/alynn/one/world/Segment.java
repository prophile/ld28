package uk.co.alynn.one.world;

import uk.co.alynn.one.Angle;

public final class Segment {
    private final Angle _angle;
    private final double _length;

    public Segment(double len, Angle angle) {
        _angle = angle;
        _length = len;
    }

    public Angle getAngle() {
        return _angle;
    }

    public double getLength() {
        return _length;
    }
}
