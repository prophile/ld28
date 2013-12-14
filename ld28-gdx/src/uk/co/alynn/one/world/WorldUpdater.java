package uk.co.alynn.one.world;

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

    public void tick() {
        // various tick components
        tickVelocity();
    }

    private void tickVelocity() {
        double dx = _constants.getDouble("speed", 0.0, "Forward speed.") * _dt;
        tickMovement(dx);
    }

    private Position advancePosition(Position pos, double dx) {
        int segmentIdx = pos.getSegmentIndex();
        double segmentLoc = pos.getPosition();
        Segment currentSegment = _world.getSegment(segmentIdx);
        Side segmentSide = pos.getSide();
        double currentSegmentLength = currentSegment.getLength();
        if (segmentLoc + dx > currentSegmentLength) {
            return advancePosition(new Position(segmentIdx + 1, 0.0,
                    segmentSide), dx - (currentSegmentLength - segmentLoc));
        } else {
            return new Position(segmentIdx, segmentLoc + dx, segmentSide);
        }
    }

    private void tickMovement(double dx) {
        Player player = _world.getPlayer();
        Position oldPosition = player.getPosition();
        Position newPosition = advancePosition(oldPosition, dx);
        player.setPosition(newPosition);
    }
}
