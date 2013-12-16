package uk.co.alynn.one.world;

import java.util.Iterator;

import uk.co.alynn.one.Constants;
import uk.co.alynn.one.render.FXManager;
import uk.co.alynn.one.sound.SoundManager;
import uk.co.alynn.one.world.level.LevelUtil;

import com.badlogic.gdx.math.Vector2;

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
        SoundManager.playSound("Switch_side");
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

    public void tick(FXManager _fxManager) throws GameOverException {
        // various tick components
        tickVelocity(_fxManager);
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

    private void tickVelocity(FXManager fxm) throws GameOverException {
        double dx = playerSpeed() * _dt;
        tickMovement(dx, fxm);
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

    private void tickMovement(double dx, FXManager fxm)
            throws GameOverException {
        Player player = _world.getPlayer();
        Position oldPosition = player.getPosition();
        Position newPosition = advancePosition(oldPosition, dx);
        collisionsInRange(oldPosition.getT(), newPosition.getT(), fxm);
        player.setPosition(newPosition);
        Vector2 pony = _world.getLevel().f(newPosition.getT());
        fxm.moveTrail(pony.x, pony.y);
    }

    private void collisionsInRange(double oldPos, double newPos, FXManager fxm)
            throws GameOverException {
        Iterator<Obstacle> numbers = _world.obstaclesBetween(oldPos, newPos);
        int countHit = 0;
        while (numbers.hasNext()) {
            ++countHit;
            Obstacle activeNumber = numbers.next();
            collideWithObstacle(activeNumber, fxm);
        }
        if (countHit > 0) {
            System.err.println("Hit " + countHit + " numbers between " + oldPos
                    + " and " + newPos);
        }
    }

    private void collideWithObstacle(Obstacle num, FXManager fxm)
            throws GameOverException {
        if (num.isPhantom()) {
            return;
        }
        if (num.getPosition().getSide() == _world.getPlayer().getPosition()
                .getSide()) {
            hitObstacle(num, fxm);
            SoundManager.playSound("Smashing_1");
        } else {
            passObstacle(num, fxm);
        }
    }

    private void hitObstacle(Obstacle num, FXManager fxm)
            throws GameOverException {
        Vector2 eyes = obstacleFXLocation(num, 0.0f);
        fxm.poof1(eyes.x, eyes.y);
        int value = num.getValue();
        if (value == 1) {
            collectObstacle(num);
        } else {
            experienceObstacle(num);
        }
    }

    private void collectObstacle(Obstacle num) {
        // do something
        _world.getPlayer().setScore(_world.getPlayer().getScore() + 1);
        _scoredPoint = true;
        num.setValue(0);
    }

    private void experienceObstacle(Obstacle num) throws GameOverException {
        SoundManager.playSound("crash_1");
    	throw new GameOverException();
    }

    private Vector2 obstacleFXLocation(Obstacle n, float additionalHeight) {
        float trackHeight = n.getPosition().getSide() == Side.SIDE_A ? 1.0f
                : -1.0f;
        trackHeight *= 25.0f + additionalHeight;
        double t = n.getPosition().getT();
        Vector2 eyes = LevelUtil.position(_world.getLevel(), t, trackHeight);
        return eyes;
    }

    private void passObstacle(Obstacle num, FXManager fxm) {
        int oldValue = num.getValue();
        num.setValue(oldValue - 1);
        Vector2 eyes = obstacleFXLocation(num, 40.0f);
        fxm.goat(eyes.x, eyes.y);
        SoundManager.playSound("Reducing_by_1");
    }
}
