
package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import inf112.skeleton.app.view.ui.game_ui.level.LevelUpDrawer;
import org.lwjgl.opengl.GL20;

public class LevelUpScreen implements Screen {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private float transitionAlpha;
    private final ShapeRenderer shapeRenderer;
    private final int level;

    private final LevelUpDrawer levelUpDrawer;

    public LevelUpScreen(int level) {
        this(level, new ShapeRenderer(), new SpriteBatch(), new OrthographicCamera()) ;
    }

    public LevelUpScreen(int level,
                         ShapeRenderer shapeRenderer,
                         SpriteBatch spriteBatch,
                         OrthographicCamera camera) {
        this.shapeRenderer = shapeRenderer;
        this.batch = spriteBatch;
        this.camera = camera;
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.level = level;
        transitionAlpha = 0.001f;
        levelUpDrawer = new LevelUpDrawer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clearScreen();

        prepareCamera();

        levelUpDrawer.draw(batch, camera, level);

        batch.end();

        transitionAlpha = ScreenManager.transitionEffect1(transitionAlpha, 0f, 1f, 0.75f, shapeRenderer);

        if (transitionAlpha > 0.95f) {
            ScreenManager.getInstance().showGameScreen();
        }
    }

    private void clearScreen(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    private void prepareCamera() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
    }

}
