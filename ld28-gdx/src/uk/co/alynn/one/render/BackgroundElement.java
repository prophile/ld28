package uk.co.alynn.one.render;

import java.util.Random;

class BackgroundElement {
    private final String _type;
    private final Random _rng;

    private float _x, _y;
    private float _vx;
    private float _vy;

    private final static float BOUND = 1000.0f;

    public BackgroundElement(String type, Random rng) {
        _type = type;
        _rng = rng;
        _x = (float) _rng.nextGaussian() * 250.0f;
        _y = (float) _rng.nextGaussian() * 250.0f;
        _vx = (float) _rng.nextGaussian() * 20.0f;
        _vy = (float) _rng.nextGaussian() * 20.0f;
    }

    public float getX() {
        return _x;
    }

    public float getY() {
        return _y;
    }

    public void update(float dt) {
        _x += dt * _vx;
        _y += dt * _vy;
        if (_x > BOUND) {
            _vx = -_vx;
            _x = BOUND;
        }
        if (_x < -BOUND) {
            _vx = -_vx;
            _x = -BOUND;
        }
        if (_y > BOUND) {
            _vy = -_vy;
            _y = BOUND;
        }
        if (_y < -BOUND) {
            _vy = -_vy;
            _y = -BOUND;
        }
    }

    public String getType() {
        return _type;
    }

}
