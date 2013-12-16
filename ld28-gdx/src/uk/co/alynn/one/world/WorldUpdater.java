package uk.co.alynn.one.world;

import java.util.Iterator;

import uk.co.alynn.one.Constants;

public final class WorldUpdater {
    private final World _world;
    private final Constants _constants;
    private final double _dt;
    private boolean _scoredPoint = false;

    public WorldUpdater(World w, Constants k, double dt) {
        _world = w;
        _constants = k;
        _dt = dt;
    }

    public World getWorld() {
        return _world;
    }

    public double getDT() {
        return _dt;
    }

    public void doFlip() {
        Player player = _world.getPlayer();
        Position oldPosition = player.getPosition();
        Position newPosition = new Position(oldPosition.getT(),
                otherSide(oldPosition.getSide()));
        player.setPosition(newPosition);
        System.err.println("wup");
    }

    private static Side otherSide(Side x) {
        switch (x) {
        case SIDE_A:
            return Side.SIDE_B;
        case SIDE_B:
            return Side.SIDE_A;
        default:
            throw new RuntimeException("Non-side encountered.");
        }
    }

    public void tick() throws GameOverException {
        // various tick components
        tickVelocity();
        tickNumberReload();
    }

    public void tickNumberReload() {
        if (shouldReloadNumbers()) {
            reloadNumbers();
        }
    }

    private boolean shouldReloadNumbers() {
        return _scoredPoint
                && getRemainingPoints() < _constants.getInt("reload-threshold",
                        2,
                        "Score threshold under which everything is reloaded.");
    }

    private int getRemainingPoints() {
        int total = 0;
        Iterator<Obstacle> obs = _world.obstaclesBetween(0.0, 1.0);
        while (obs.hasNext()) {
            Obstacle eyes = obs.next();
            total += eyes.getValue();
        }
        return total;
    }

    private void reloadNumbers() {
        ObstacleLoader.loadObstacles(_world, "numbers", true);
    }

    private void tickVelocity() throws GameOverException {
        double dx = playerSpeed() * _dt;
        tickMovement(dx);
    }

    private double playerSpeed() {
        return _constants.getDouble("speed", 0.0, "Forward speed.")
                / _world.getLevel()
                        .fDerivative(_world.getPlayer().getPosition().getT())
                        .len();
    }

    private Position advancePosition(Position pos, double dx) {
        return new Position(pos.getT() + dx, pos.getSide());
    }

    private void tickMovement(double dx) throws GameOverException {
        Player player = _world.getPlayer();
        Position oldPosition = player.getPosition();
        Position newPosition = advancePosition(oldPosition, dx);
        collisionsInRange(oldPosition.getT(), newPosition.getT());
        player.setPosition(newPosition);
    }

    private void collisionsInRange(double oldPos, double newPos)
            throws GameOverException {
        Iterator<Obstacle> numbers = _world.obstaclesBetween(oldPos, newPos);
        int countHit = 0;
        while (numbers.hasNext()) {
            ++countHit;
            Obstacle activeNumber = numbers.next();
            collideWithObstacle(activeNumber);
        }
        if (countHit > 0) {
            System.err.println("Hit " + countHit + " numbers between " + oldPos
                    + " and " + newPos);
        }
    }

    private void collideWithObstacle(Obstacle num) throws GameOverException {
        if (num.isPhantom()) {
            return;
        }
        if (num.getPosition().getSide() == _world.getPlayer().getPosition()
                .getSide()) {
            hitObstacle(num);
        } else {
            passObstacle(num);
        }
    }

    private void hitObstacle(Obstacle num) throws GameOverException {
        int value = num.getValue();
        if (value == 1) {
            collectObstacle(num);
        } else {
            experienceObstacle(num);
        }
    }

    private void collectObstacle(Obstacle num) {
        // do something
        num.setValue(0);
        _world.getPlayer().setScore(_world.getPlayer().getScore() + 1);
        _scoredPoint = true;
    }

    private void experienceObstacle(Obstacle num) throws GameOverException {
        throw new GameOverException();
    }

    private void passObstacle(Obstacle num) {
        int oldValue = num.getValue();
        if (oldValue > 0) {
            num.setValue(oldValue - 1);
        }
    }
}
