package uk.co.alynn.one.world;

final class ObstacleGenerator {
    private final World _world;
    private final boolean _avoidPlayer;

    public ObstacleGenerator(World world, boolean avoidPlayer) {
        _world = world;
        _avoidPlayer = avoidPlayer;
    }

    public World getWorld() {
        return _world;
    }

    public void addObstacle(int value, double position, Side side) {
        Position rawPosition = new Position(position * 0.01, side);
        if (shouldSkip(rawPosition)) {
            return;
        }
        System.out.println(rawPosition);
        Obstacle number = new Obstacle(rawPosition);
        number.setValue(value);
        _world.attachObstacle(number);
    }

    private boolean shouldSkip(Position rawPosition) {
        return _avoidPlayer
                && rawPosition.distanceTo(_world.getPlayer().getPosition()) < 0.08;
    }
}
