package uk.co.alynn.one.world;

final class NumberGenerator {
    private final World _world;

    public NumberGenerator(World world) {
        _world = world;
    }

    public World getWorld() {
        return _world;
    }

    public void addNumber(int value, double position, Side side) {
        Position rawPosition = new Position(position * 0.01, side);
        System.out.println(rawPosition);
        Number number = new Number(rawPosition);
        number.setValue(value);
        _world.attachNumber(number);
    }
}
