package inf112.skeleton.app.model.score;

import inf112.skeleton.app.event.Event;

public class ScoreEvent extends Event {
    private final int points;

    public ScoreEvent(Object caller, int points) {
        super(caller);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
