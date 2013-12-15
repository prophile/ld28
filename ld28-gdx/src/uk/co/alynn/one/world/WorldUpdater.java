package uk.co.alynn.one.world;

import java.util.Iterator;

import uk.co.alynn.one.Constants;

public final class WorldUpdater {
    private final World _world;
    private final Constants _constants;
    private final double _dt;

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
    }

    private void tickVelocity() throws GameOverException {
        double dx = _constants.getDouble("speed", 0.0, "Forward speed.") * _dt;
        tickMovement(dx);
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
        Iterator<Number> numbers = _world.numbersBetween(oldPos, newPos);
        while (numbers.hasNext()) {
            Number activeNumber = numbers.next();
            collideWithNumber(activeNumber);
        }
    }

    private void collideWithNumber(Number num) throws GameOverException {
        if (num.getPosition().getSide() == _world.getPlayer().getPosition()
                .getSide()) {
            hitNumber(num);
        } else {
            passNumber(num);
        }
    }

    private void hitNumber(Number num) throws GameOverException {
        int value = num.getValue();
        if (value == 1) {
            collectNumber(num);
        } else {
            experienceNumber(num);
        }
    }

    private void collectNumber(Number num) {
        // do something
        num.setValue(0);
        _world.getPlayer().setScore(_world.getPlayer().getScore() + 1);
    }

    private void experienceNumber(Number num) throws GameOverException {
        throw new GameOverException();
    }

    private void passNumber(Number num) {
        int oldValue = num.getValue();
        if (oldValue > 0) {
            num.setValue(oldValue - 1);
        }
    }
}
