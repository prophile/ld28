package uk.co.alynn.one.world;

import java.util.Iterator;

class ChainedIterator<T> implements Iterator<T> {
    private final Iterator<T> _left;
    private final Iterator<T> _right;

    public ChainedIterator(Iterator<T> left, Iterator<T> right) {
        _left = left;
        _right = right;
    }

    @Override
    public boolean hasNext() {
        return _right.hasNext();
    }

    @Override
    public T next() {
        if (_left.hasNext()) {
            return _left.next();
        } else {
            return _right.next();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}