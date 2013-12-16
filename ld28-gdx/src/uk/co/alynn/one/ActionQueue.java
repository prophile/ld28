package uk.co.alynn.one;

public class ActionQueue {
    private boolean flipQueued;

    public ActionQueue() {
        flipQueued = false;
    }

    void queueFlip() {
        flipQueued = true;
    }

    public boolean popFlip() {
        boolean rv = flipQueued;
        flipQueued = false;
        return rv;
    }
}