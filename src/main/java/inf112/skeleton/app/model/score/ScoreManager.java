package inf112.skeleton.app.model.score;

import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;

public class ScoreManager {
    private int score;

    public ScoreManager() {
        IEventBus bus = GlobalEventBus.getInstance(1);
        bus.on("EnemyDefeated", this::handleScoreEvent);
        bus.on("LevelCompleted", this::handleScoreEvent);
    }

    private void handleScoreEvent(Event event) {
        if (event instanceof ScoreEvent se) {
            addPoints(se.getPoints());
        }
    }

    private void addPoints(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }
}
