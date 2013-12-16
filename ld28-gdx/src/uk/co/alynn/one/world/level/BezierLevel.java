package uk.co.alynn.one.world.level;

import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class BezierLevel implements Level {
    private final List<Vector2> _nodes;

    public BezierLevel(List<Vector2> nodes) {
        _nodes = Collections.unmodifiableList(nodes);
    }

    @Override
    public Vector2 f(double x) {
        x *= _nodes.size() - 1;
        int item = (int) x;
        Vector2 vertex = _nodes.get(item % _nodes.size());
        Vector2 vertex_ = _nodes.get((item + 1) % _nodes.size());
        double delta = x - item;
        return new Vector2(lerp(delta, vertex.x, vertex_.x), lerp(delta,
                vertex.y, vertex_.y));
    }

    private static float lerp(double delta, float x, float y) {
        float delta_ = (float) delta;
        return (1.0f - delta_) * x + delta_ * y;
    }

    @Override
    public Vector2 fDerivative(double x) {
        return LevelUtil.derivativeDefault(this, x);
    }

}
