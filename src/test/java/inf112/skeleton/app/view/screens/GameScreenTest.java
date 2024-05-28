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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.view.ViewableModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameScreenTest {
    private GameScreen gameScreen;
    private HeadlessApplication application;

    private SpriteBatch mockBatch;
    private OrthographicCamera mockCamera;
    private Array<Entity> mockEntities;

    @BeforeEach
    void setUp() {
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

        ViewableModel mockModel = mock(GameModel.class);
        when(mockModel.getMap()).thenReturn(createMockMap());
        when(mockModel.getPlayerPosition()).thenReturn(new Vector2(0, 0));

        this.mockEntities = new Array<>();
        Entity mockEntity = mock(Entity.class);
        mockEntities.add(mockEntity);
        when(mockEntity.getPosition()).thenReturn(new Vector2(0, 0));
        when(mockModel.getEntities()).thenReturn(mockEntities);

        Media.load_assets();

        mockBatch = mock(SpriteBatch.class);
        mockCamera = new OrthographicCamera();
        ShapeRenderer mockShapeRenderer = mock(ShapeRenderer.class);

        // Mock the static methods in Gdx that handle graphics and files
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(800);
        when(Gdx.graphics.getHeight()).thenReturn(600);

        init();

        gameScreen = new GameScreen(mockModel, mockBatch, mockShapeRenderer, mockCamera);
        gameScreen.getCamera().zoom = 1.5f;
    }

    private Map createMockMap() {
        InsaneMapFactory mapFactory = new InsaneMapFactory();
        return mapFactory.generate(100, 100, 1);
    }

    private void init() {
        Map mockMap = createMockMap();
        Box2DWorldStatic.startBox2DWorld();
        Box2DWorldStatic.loadMap(mockMap, new DefaultTileBodyCreator());
    }

    @Test
    public void testConstructor() {
        Assertions.assertNotNull(gameScreen, "GameScreen should be initialized");
    }

    @Test
    void testShow() {
        gameScreen.show();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    void testRender() {
        gameScreen.render(0.1f, mockEntities);
        verify(mockBatch, atLeastOnce()).begin();
        verify(mockBatch, atLeastOnce()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, atLeastOnce()).end();
    }

    @Test
    void testGetCamera() {
        assertEquals(mockCamera, gameScreen.getCamera());
    }

    @Test
    void testResize() {
        int width = 800;
        int height = 600;
        gameScreen.resize(width, height);
        assertEquals((float) width / height * 5, gameScreen.getCamera().viewportWidth);
        assertEquals(5, gameScreen.getCamera().viewportHeight);
    }

    @Test
    void testPause() {
        gameScreen.pause();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    void testResume() {
        gameScreen.resume();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    void testHide() {
        gameScreen.hide();
        verify(mockBatch, never()).begin();
        verify(mockBatch, never()).end();
    }

    @Test
    void testDispose() {
        gameScreen.dispose();
        verify(mockBatch).dispose();
    }
}
