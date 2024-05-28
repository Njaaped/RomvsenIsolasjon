package inf112.skeleton.app.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.RomvsenIsolasjon;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.gamestate.GameState;
import inf112.skeleton.app.model.mapfactories.*;
import inf112.skeleton.app.model.entity.alien.Alien;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.view.screens.GameScreen;
import inf112.skeleton.app.view.screens.ScreenManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameModelTest {
    private GameModel gameModel;
    private HeadlessApplication application;

    @BeforeEach
    public void setup() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        }, config);

        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = mock(GL20.class);


        this.gameModel = new GameModel(new InsaneMapFactory());

        Media.load_assets();

        GameModel.state = GameState.RUNNING;

    }

    @Test
    public void testInitialization() {
        assertNotNull(gameModel.map);
        assertNotNull(gameModel.player);
        assertEquals(GameState.RUNNING, GameModel.state);
        assertNotNull(gameModel.scoreManager);
        assertNotNull(gameModel.soundEffectHandler);
        assertNotNull(gameModel.musicHandler);
    }

    @Test
    public void testGetEntities() {
        Array<Entity> entities = gameModel.getEntities();
        assertNotNull(entities);
        assertTrue(entities.size > 0);
    }

    @Test
    public void testGetTiles() {
        Array<Array<Tile>> tiles = gameModel.getTiles();
        assertNotNull(tiles);
        assertTrue(tiles.size > 0);
    }

    @Test
    public void testGetMap() {
        Map map = gameModel.getMap();
        assertNotNull(map);
    }

    @Test
    public void testGetPlayerPosition() {
        Vector2 position = gameModel.getPlayerPosition();
        assertNotNull(position);
    }

    @Test
    public void testGetPlayerHealthPercentage() {
        float healthPercentage = gameModel.getPlayerHealthPercentage();
        assertTrue(healthPercentage >= 0 && healthPercentage <= 100);
    }

    @Test
    public void testMoveDirection() {
        gameModel.moveDirection(1, 0);
        assertEquals(new Vector2(1, 0), gameModel.player.getMovementDirection());
    }

    @Test
    public void testIsSprinting() {
        gameModel.isSprinting(false);
        float speed = gameModel.player.getSpeed();
        gameModel.isSprinting(true);
        assertEquals(speed*2, gameModel.player.getSpeed());
    }

    @Test
    public void testUpdate() {
        gameModel.update(1f);
        // Verify that the game state is updated correctly
    }

    @Test
    public void testPauseGame() {
        gameModel.pauseGame();
        assertEquals(GameState.PAUSED, GameModel.state);
        gameModel.pauseGame();
        assertEquals(GameState.RUNNING, GameModel.state);
    }

    @Test
    public void testGameOver() {
        gameModel.gameOver();
        assertEquals(GameState.GAME_OVER, GameModel.state);
    }

    @Test
    public void testRestartGame() {
        gameModel.restartGame();
        assertEquals(GameState.RUNNING, GameModel.state);
        assertNotNull(gameModel.map);
        assertNotNull(gameModel.player);
    }

    @Test
    public void testStopGame() {
        gameModel.stopGame();
        assertEquals(GameState.MAIN_MENU, GameModel.state);
        assertNotNull(gameModel.map);
        assertNotNull(gameModel.player);
    }

    @Test
    public void testGetScore() {
        int score = gameModel.getScore();
        assertEquals(0, score);
    }
}
