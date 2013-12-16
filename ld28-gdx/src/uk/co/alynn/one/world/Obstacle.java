package uk.co.alynn.one.world;

public final class Obstacle implements Comparable<Obstacle> {
    private final Position _pos;
    private int _value;

    public Obstacle(Position pos) {
        _pos = pos;
        _value = 1;
    }

    public Position getPosition() {
        return _pos;
    }

    public int getValue() {
        return _value;
    }

    public boolean isPhantom() {
        return getValue() == 0;
    }

    public void setValue(int val) {
        if (val < 0) {
            throw new IllegalArgumentException("Numbers must be non-negative.");
        }
        _value = val;
    }

    @Override
    public int compareTo(Obstacle other) {
        return _pos.compareTo(other._pos);
    }

    @Override
    public String toString() {
        return "N<" + _value + ", " + _pos + ">";
    }
}
