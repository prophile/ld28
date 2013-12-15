package uk.co.alynn.one.world;

final class NumberGenerator {
    private final World _world;

    public NumberGenerator(World world) {
        _world = world;
    }

    public World getWorld() {
        return _world;
    }

    private Position rawDistanceToPosition(double distance, Side side) {
        int index = 0;
        // ugly
        while (_world.getSegment(index).getLength() >= distance) {
            distance -= _world.getSegment(index).getLength();
            ++index;
        }
        return new Position(index, distance, side);
    }

    public void addNumber(int value, double position, Side side) {
        Number number = new Number(rawDistanceToPosition(position, side));
        number.setValue(value);
        _world.attachNumber(number);
    }
}