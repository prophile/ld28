package uk.co.alynn.one.world;

public final class Player {
    private Position _pos;
    private int _score;

    public Player() {
        _pos = new Position(0.0, Side.SIDE_A);
        _score = 0;
    }

    public Position getPosition() {
        return _pos;
    }

    public void setPosition(Position pos) {
        _pos = pos;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException(
                    "Score must be strictly nonnegative.");
        }
        if (score < _score) {
            throw new IllegalArgumentException("Score must never decrease.");
        }
        _score = score;
    }
}
