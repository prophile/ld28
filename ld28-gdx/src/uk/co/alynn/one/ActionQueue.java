package uk.co.alynn.one;

class ActionQueue {
    private boolean flipQueued;

    public ActionQueue() {
        flipQueued = false;
    }

    void queueFlip() {
        flipQueued = true;
    }

    boolean popFlip() {
        boolean rv = flipQueued;
        flipQueued = false;
        return rv;
    }
}