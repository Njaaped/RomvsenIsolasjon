package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import inf112.skeleton.app.controller.AppController;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.view.ui.buttons.GameButton;


/**
 * Represents the start screen of the game, providing options to start the game or exit.
 * This screen includes interactive buttons and handles transitions to other screens.
 */
public class StartScreen implements Screen {
    private final GameButton startButton;
    private final GameButton exitButton;
    private final Texture startButtonOff;
    private final Texture startButtonOn;
    private final Texture exitButtonOff;
    private final Texture exitButtonOn;
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera camera;
    private float transitionAlpha;
    private ShapeRenderer shapeRenderer;

    private AppController appController;

    public StartScreen() {
        this(new SpriteBatch(), new ShapeRenderer(), new OrthographicCamera(), ScreenManager.getInstance(), new AppController());
    }

    /**
     * Constructs the start screen with all necessary UI components and initializes the screen layout.
     */
    public StartScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, OrthographicCamera camera, ScreenManager screenManager, AppController appController) {
        this.batch = batch;
        background = Media.getTexture("startBackground");
        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        transitionAlpha = 0.0f;
        this.appController = appController;

        startButtonOff = Media.getTexture("buttonOffStart");
        startButtonOn = Media.getTexture("buttonOnStart");
        exitButtonOff = Media.getTexture("buttonOffExit");
        exitButtonOn = Media.getTexture("buttonOnExit");

        int buttonWidth = Gdx.graphics.getWidth() / 5;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        int buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        int startButtonY = Gdx.graphics.getHeight() / 2 - buttonHeight / 2;
        int exitButtonY = Gdx.graphics.getHeight() / 2 - buttonHeight / 2 - buttonHeight - 10;

        startButton = new GameButton(startButtonOff, startButtonOn, new Rectangle(buttonX, startButtonY, buttonWidth, buttonHeight));
        exitButton = new GameButton(exitButtonOff, exitButtonOn, new Rectangle(buttonX, exitButtonY, buttonWidth, buttonHeight));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        startButton.update(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        startButton.draw(batch);
        exitButton.update(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        exitButton.draw(batch);

        handleMouseClick();

        batch.end();

        transition();
        changeIfDarkEnough();
    }

    void handleMouseClick() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (startButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                transitionAlpha = 0.001f;
            } else if (exitButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                appController.exit();
            }
        }
    }

    private void transition() {
        transitionAlpha = ScreenManager.transitionEffect1(transitionAlpha, 0f, 1f, 5f, shapeRenderer);
    }

    private void changeIfDarkEnough() {
        if (transitionAlpha > 0.95f) {
            ScreenManager.getInstance().showGameScreen();
        }
    }


    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        int buttonWidth = Gdx.graphics.getWidth() / 4;
        int buttonHeight = Gdx.graphics.getHeight() / 6;
        int buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        int startButtonY = 3 * Gdx.graphics.getHeight() / 7 - buttonHeight / 2;
        int exitButtonY = 3 * Gdx.graphics.getHeight() / 7 - buttonHeight / 2 - buttonHeight - 25;

        startButton.setBounds(new Rectangle(buttonX, startButtonY, buttonWidth, buttonHeight));
        exitButton.setBounds(new Rectangle(buttonX, exitButtonY, buttonWidth, buttonHeight));
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
        batch.dispose();
        background.dispose();
        shapeRenderer.dispose();
        startButton.dispose();
        exitButton.dispose();
        startButtonOff.dispose();
        startButtonOn.dispose();
        exitButtonOff.dispose();
        exitButtonOn.dispose();
    }

    /**
     * Retrieves the current transition alpha value for the screen.
     *
     * @return the current transition alpha value
     */
    public float getTransitionAlpha() {
        return transitionAlpha;
    }

    /**
     * Sets the transition alpha value for the screen.
     *
     * @param transitionAlpha the new transition alpha value
     */
    public void setTransitionAlpha(float transitionAlpha) {
        this.transitionAlpha = transitionAlpha;
    }

    /**
     * Retrieves the SpriteBatch used for rendering the screen.
     *
     * @return the SpriteBatch used for rendering
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Sets the SpriteBatch used for rendering the screen.
     *
     * @param batch the new SpriteBatch to use for rendering
     */
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    /**
     * Retrieves the OrthographicCamera used for rendering the screen.
     *
     * @return the OrthographicCamera used for rendering
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Sets the OrthographicCamera used for rendering the screen.
     *
     * @param camera the new OrthographicCamera to use for rendering
     */
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Retrieves the ShapeRenderer used for rendering shapes on the screen.
     *
     * @return the ShapeRenderer used for rendering shapes
     */
    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    /**
     * Sets the ShapeRenderer used for rendering shapes on the screen.
     *
     * @param shapeRenderer the new ShapeRenderer to use for rendering shapes
     */
    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

}