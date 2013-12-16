package uk.co.alynn.one.world;

final class ObstacleGenerator {
    private final World _world;

    public ObstacleGenerator(World world) {
        _world = world;
    }

    public World getWorld() {
        return _world;
    }

    public void addObstacle(int value, double position, Side side) {
        Position rawPosition = new Position(position * 0.01, side);
        System.out.println(rawPosition);
        Obstacle number = new Obstacle(rawPosition);
        number.setValue(value);
        _world.attachObstacle(number);
    }
}
