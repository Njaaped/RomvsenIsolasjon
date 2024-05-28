package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LevelUpScreenTest {
    private LevelUpScreen levelUpScreen;

    private final int level = 1;
    private ShapeRenderer mockShapeRenderer;
    private SpriteBatch mockBatch;
    private OrthographicCamera mockCamera;

    @BeforeEach
    public void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication application = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void render() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }
        }, config);

        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = mock(GL20.class);

        this.mockShapeRenderer = mock(ShapeRenderer.class);
        this.mockBatch = mock(SpriteBatch.class);
        this.mockCamera = new OrthographicCamera();

        levelUpScreen = new LevelUpScreen(level, mockShapeRenderer, mockBatch, mockCamera);

        // Mock the static methods in Gdx that handle graphics and files
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(800);
        when(Gdx.graphics.getHeight()).thenReturn(600);
    }

    // Test that the constructor initializes the LevelUpScreen object
    @Test
    public void testConstructor() {
        assertNotNull(levelUpScreen, "LevelUpScreen should be initialized");
    }

    @Test
    public void testShow() {
        levelUpScreen.show();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testRender() {
        levelUpScreen.render(0.1f);
        verify(mockBatch, atLeastOnce()).begin();
        verify(mockBatch, atLeastOnce()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, atLeastOnce()).end();
    }

    @Test
    public void testResize() {
        levelUpScreen.resize(800, 600);
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testPause() {
        levelUpScreen.pause();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testResume() {
        levelUpScreen.resume();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testHide() {
        levelUpScreen.hide();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    public void testDispose() {
        levelUpScreen.dispose();
        verify(mockBatch, atLeastOnce()).dispose();
    }

}
