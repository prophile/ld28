package uk.co.alynn.one.world;

public final class Number implements Comparable<Number> {
    private final Position _pos;
    private int _value;

    public Number(Position pos) {
        _pos = pos;
        _value = 1;
    }

    public Position getPosition() {
        return _pos;
    }

    public int getValue() {
        return _value;
    }

    public void setValue(int val) {
        if (val <= 0) {
            throw new IllegalArgumentException(
                    "Numbers must be classical naturals.");
        }
        _value = val;
    }

    @Override
    public int compareTo(Number other) {
        return _pos.compareTo(other._pos);
    }
}
