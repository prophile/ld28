package uk.co.alynn.one.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public final class World {
    private final Player _player;
    private final NavigableSet<Number> _numbers;
    private final List<Segment> _segments;

    public World(List<Segment> segments) {
        _player = new Player();
        _numbers = new TreeSet<Number>();
        _segments = Collections.unmodifiableList(segments);
    }

    public Player getPlayer() {
        return _player;
    }

    public Iterator<Number> numbersBetween(Position leftBound,
            Position rightBound) {
        NavigableSet<Number> range = _numbers.subSet(new Number(leftBound),
                true, new Number(rightBound), false);
        return range.iterator();
    }

    public Segment getSegment(int index) {
        // horrendous, there must be a nicer way
        int size = _segments.size();
        while (index < size) {
            index += size;
        }
        return _segments.get(index % size);
    }

    void attachNumber(Number n) {
        _numbers.add(n);
    }

    public void attachAllNumbers(BufferedReader source) throws IOException {
        NumberGenerator gen = new NumberGenerator(this);
        NumberFileParser parser = new NumberFileParser(gen, source);
        parser.process();
        for (Number n : _numbers) {
            System.out.println(n);
        }
    }
}
