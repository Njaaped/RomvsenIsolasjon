package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import inf112.skeleton.app.controller.AppController;
import inf112.skeleton.app.view.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StartScreenTest {
    @Mock private SpriteBatch mockBatch;
    @Mock private ShapeRenderer mockShapeRenderer;
    @Mock private ScreenManager mockScreenManager;
    @Mock private OrthographicCamera mockCamera;
    @Mock private Input mockInput;
    @Mock private Graphics mockGraphics;
    @Mock private AppController mockAppController;

    private StartScreen startScreen;
    private HeadlessApplication application;

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

        MockitoAnnotations.openMocks(this);
        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = mock(GL20.class);
        Gdx.graphics = mockGraphics;
        Gdx.input = mockInput;
        when(mockGraphics.getWidth()).thenReturn(800);
        when(mockGraphics.getHeight()).thenReturn(600);

        Media.load_assets();

        startScreen = new StartScreen(mockBatch, mockShapeRenderer, mockCamera, mockScreenManager, mockAppController);
    }

    @Test
    public void testRender_withClickOnStartButton() {
        when(mockInput.getX()).thenReturn(400);
        when(mockInput.getY()).thenReturn(300);
        when(mockInput.isButtonJustPressed(Input.Buttons.LEFT)).thenReturn(true);

        startScreen.handleMouseClick();

        assertEquals(0.001f, startScreen.getTransitionAlpha());
    }

    @Test
    public void testRender_withClickOnExitButton() {
        when(mockInput.getX()).thenReturn(400);
        when(mockInput.getY()).thenReturn(350);
        when(mockInput.isButtonJustPressed(Input.Buttons.LEFT)).thenReturn(true);

        startScreen.handleMouseClick();

        verify(mockAppController, atLeastOnce()).exit();
        Gdx.app.exit();
    }

    @Test
    public void testShowAndHide() {
        startScreen.show();
        startScreen.hide();
        // Verify interactions or state changes if any expected
    }

    @Test
    public void testDisposeResources() {
        startScreen.dispose();
        verify(mockBatch, times(1)).dispose();
        verify(mockShapeRenderer, times(1)).dispose();
    }

    @Test
    public void testResize() {
        startScreen.resize(1024, 768);
        // verify(mockBatch, atLeastOnce()); // Check for any batch operations if needed
    }

    @Test
    public void testPauseAndResume() {
        startScreen.pause();
        startScreen.resume();
        // Verify interactions or state changes if any expected
    }

    @Test
    public void testTransitionAlphaGetterAndSetter() {
        startScreen.setTransitionAlpha(0.5f);
        assertEquals(0.5f, startScreen.getTransitionAlpha(), "The transition alpha should be set to 0.5");
    }

    @Test
    public void testBatchGetterAndSetter() {
        SpriteBatch newBatch = mock(SpriteBatch.class);
        startScreen.setBatch(newBatch);
        assertEquals(newBatch, startScreen.getBatch(), "The batch should be set to the new SpriteBatch instance");
    }

    @Test
    public void testCameraGetterAndSetter() {
        OrthographicCamera newCamera = mock(OrthographicCamera.class);
        startScreen.setCamera(newCamera);
        assertEquals(newCamera, startScreen.getCamera(), "The camera should be set to the new OrthographicCamera instance");
    }

    @Test
    public void testShapeRendererGetterAndSetter() {
        ShapeRenderer newShapeRenderer = mock(ShapeRenderer.class);
        startScreen.setShapeRenderer(newShapeRenderer);
        assertEquals(newShapeRenderer, startScreen.getShapeRenderer(), "The shape renderer should be set to the new ShapeRenderer instance");
    }
}
