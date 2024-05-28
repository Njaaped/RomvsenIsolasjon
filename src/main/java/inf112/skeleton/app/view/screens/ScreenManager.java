package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;
import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.model.audio.MusicEvent;


/**
 * This class is used to manage the different screens of the game.
 * 
 * 
 */


public class ScreenManager {
    private static ScreenManager instance;
    private Game game;
    private GameModel model;

    ScreenManager() {
    }

    /**
     * This method is used to create a singleton instance of the ScreenManager class.
     * @return
     */

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * This method is used to initialize the ScreenManager with a game and a model.
     * @param game
     * @param model
     */

    public void initialize(Game game, GameModel model) {
        this.game = game;
        this.model = model;
        showStartScreen();
    }

    /**
     * This method is used to set the current screen of the game.
     * @param screen
     */

    public void setScreen(Screen screen) {
        game.setScreen(screen);
    }

    /**
     * This method is used to show the start screen of the game.
     */

    public void showStartScreen() {
        Screen currentScreen = game.getScreen();

        if (currentScreen != null) {
            currentScreen.dispose();
        }

        Screen newScreen = new StartScreen();
        game.setScreen(newScreen);
        IEventBus bus = GlobalEventBus.getInstance(3);
        bus.trigger("PlayMusic", new MusicEvent(this, "loading"));
    }

    /**
     * This method is used to show the game screen of the game.
     */

    public void showGameScreen() {
        Screen currentScreen = game.getScreen();

        if (currentScreen != null) {
            currentScreen.dispose();
        }

        Screen newScreen = new GameScreen(model);
        game.setScreen(newScreen);
        IEventBus bus = GlobalEventBus.getInstance(3);
        bus.trigger("PlayMusic", new MusicEvent(this, "battle"));
    }

    /**
     * This method is used to show the game over screen of the game.
     */

    public void showGameOverScreen() {
        Screen currentScreen = game.getScreen();

        if (currentScreen != null) {
            currentScreen.dispose();
        }

        Screen newScreen = new GameOverScreen(model);
        game.setScreen(newScreen);

        IEventBus bus = GlobalEventBus.getInstance(3);
        bus.trigger("PlayMusic", new MusicEvent(this, "loading"));
    }

    /**
     * This method is used to show the level up screen of the game.
     *
     * @return
     */

    public void showLevelUpScreen(int level) {
        Screen currentScreen = game.getScreen();

        if (currentScreen != null) {
            currentScreen.dispose();
        }

        Screen newScreen = new LevelUpScreen(level);
        game.setScreen(newScreen);
    }

    /**
     * This method is used to get the current screen of the game.
     * @return
     */

    public Screen getCurrentScreen() {
        return game.getScreen();
    }

    /**
     * This method is used to dispose the current instance of the ScreenManager.
     */

    public void dispose() {
        instance = null;
    }

    /**
     * This method is used to create a transition effect between screens. 
     * start at 1 and end at 0 to fade in, transparent to opaque, and start at 0 and end at 1 to fade out, opaque to transparent.
     * @param current
     * @param start
     * @param end
     * @param speed
     * @param shapeRenderer
     * @return
     */

    public static float transitionEffect1(float current, float start, float end, float speed, ShapeRenderer shapeRenderer) {
        if (current < 1.1f && current > 0) {
            // Render the transition effect
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0f, 0f, 0f, current);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            // Update the transition alpha
            if (end < 1.1f && end > 0.9f) {
                current = Math.min(0.999f, current + 0.01f * speed);
            } else {
                current = Math.max(0f, current - 0.01f * speed);
            }
        } 
        return current;
    }

    /**
     * This method is used to create a pause menu. creates a gray transparent overlay
     * and writes "PAUSED" in the middle of the screen.
     *
     * @param shapeRenderer
     */

    public static void pauseMenu(ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public Game getGame() {
        return game;
    }
}