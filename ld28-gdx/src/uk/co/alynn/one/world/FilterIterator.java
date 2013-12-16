package uk.co.alynn.one.world;

import java.util.Iterator;

abstract class FilterIterator<T> implements Iterator<T> {
    private final Iterator<T> _underlying;
    private T _next;
    private boolean _initialised = false;

    abstract boolean test(T x);

    private void advance() {
        while (_underlying.hasNext()) {
            _next = _underlying.next();
            if (test(_next)) {
                return;
            }
        }
        _next = null;
    }

    private void init() {
        if (!_initialised) {
            advance();
            _initialised = true;
        }
    }

    public FilterIterator(Iterator<T> underlying) {
        _underlying = underlying;
    }

    @Override
    public boolean hasNext() {
        init();
        return _next != null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T next() {
        init();
        T bees = _next;
        advance();
        return bees;
    }
}