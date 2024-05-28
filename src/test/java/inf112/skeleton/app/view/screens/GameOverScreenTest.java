package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.controller.ControllableModel;
import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.model.gamestate.GameState;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GameOverScreenTest {
    private GameOverScreen gameOverScreen;
    private HeadlessApplication application;

    private ControllableModel mockModel;
    private SpriteBatch mockBatch;
    private ScreenManager mockScreenManager;

    @BeforeEach
    public void setUp() {
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

        mockModel = mock(ControllableModel.class);
        mockScreenManager = mock(ScreenManager.class);
        mockBatch = mock(SpriteBatch.class);
        gameOverScreen = new GameOverScreen(mockModel, mockBatch);
        gameOverScreen.setScreenManager(mockScreenManager);

        // Mock the static methods in Gdx that handle graphics and files
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(800);
        when(Gdx.graphics.getHeight()).thenReturn(600);
    }

    @Test
    public void testConstructor() {
        assertNotNull(gameOverScreen, "GameOverScreen should be initialized");
    }

    @Test
    public void testShow() {
        gameOverScreen.show();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testRender() {
        gameOverScreen.render(0.1f);
        verify(mockBatch, atLeastOnce()).begin();
        verify(mockBatch, atLeastOnce()).draw(any(Texture.class), eq(0f), eq(0f), eq(800f), eq(600f));
        verify(mockBatch, atLeastOnce()).end();
    }

    @Test
    public void testResize() {
        gameOverScreen.resize(800, 600);
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testPause() {
        gameOverScreen.pause();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testResume() {
        gameOverScreen.resume();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testCheckGameOverWithNullState() {
        GameModel.state = null;
        gameOverScreen.checkGameOver();
        // Optionally verify that no methods on screenManager were called
        verifyNoInteractions(mockScreenManager);
    }

    @Test
    public void testCheckGameOverWithGameOverState() {
        GameModel.state = GameState.GAME_OVER;
        gameOverScreen.checkGameOver();
        verifyNoInteractions(mockScreenManager);
    }

    @Test
    public void testCheckGameOverWithRunningState() {
        GameModel.state = GameState.RUNNING;
        gameOverScreen.checkGameOver();
        verify(mockScreenManager).showGameScreen();
        verifyNoMoreInteractions(mockScreenManager);
    }

    @Test
    public void testCheckGameOverWithMainMenuState() {
        GameModel.state = GameState.MAIN_MENU;
        gameOverScreen.checkGameOver();
        verify(mockScreenManager).showStartScreen();
        verifyNoMoreInteractions(mockScreenManager);
    }

    @Test
    public void testHide() {
        gameOverScreen.hide();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }


    @Test
    public void testDispose() {
        SpriteBatch mockBatch = mock(SpriteBatch.class);
        Texture mockBackgroundTexture = mock(Texture.class);

        GameOverScreen screen = new GameOverScreen(mockModel, mockBatch, mockBackgroundTexture);
        screen.dispose();

        verify(mockBatch).dispose();
        verify(mockBackgroundTexture).dispose();
    }

    @AfterEach
    public void cleanUp() {
        application.exit();
        gameOverScreen.dispose();
    }
}
