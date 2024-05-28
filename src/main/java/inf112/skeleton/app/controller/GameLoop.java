package inf112.skeleton.app.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

class GameLoop extends Task implements Disposable {
    private final float intervalSeconds;

    private final ControllableModel model;
    private boolean isRunning;
    private long lastExecution;

    /**
     * Calls the update method on the model repeatedly. The loop is stopped by default, so {@link #start() start()}
     * must be called to start the loop.
     * @param model Model containing the update method
     * @param intervalSeconds Seconds between each method call
     */
    public GameLoop(ControllableModel model, float intervalSeconds) {
        this.model = model;
        this.intervalSeconds = intervalSeconds;
        this.isRunning = false;
        this.lastExecution = getExecuteTimeMillis();
        Timer.schedule(this, intervalSeconds, intervalSeconds, -1);
    }

    /**
     * Start the game loop
     */
    public void start() {
        isRunning = true;
    }
    
    @Override
    public void run() {
        long thisExecution = getExecuteTimeMillis();
        float deltaTimeSeconds = Math.min((float) (thisExecution - lastExecution), intervalSeconds * 1000f) / 1000f;
        lastExecution = thisExecution;
        if (!isRunning) {
            System.out.println("huh");
            return;
        }
        
        model.update(deltaTimeSeconds);
    }

    @Override
    public void dispose() {
        this.cancel();
    }
}
