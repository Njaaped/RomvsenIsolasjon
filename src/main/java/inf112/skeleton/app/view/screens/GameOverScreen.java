package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.controller.ControllableModel;
import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.model.gamestate.GameState;
import inf112.skeleton.app.view.ui.buttons.GameButton;

public class GameOverScreen implements Screen {
    private final ControllableModel model;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture retryButtonOff;
    private final Texture retryButtonOn;
    private final Texture mainMenuButtonOff;
    private final Texture mainMenuButtonOn;
    private final Texture exitButtonOff;
    private final Texture exitButtonOn;
    private final GameButton retryButton;
    private final GameButton mainMenuButton;
    private final GameButton exitButton;
    private ScreenManager screenManager;

    public GameOverScreen(ControllableModel model) {
        this(model, new SpriteBatch());
    }

    public GameOverScreen(ControllableModel model, SpriteBatch batch) {
        this(model, batch, new Texture(Gdx.files.internal("images/game_over_bg.png")));
    }

    public GameOverScreen(ControllableModel model, SpriteBatch batch, Texture backgroundTexture) {
        this.model = model;
        this.batch = batch;
        this.backgroundTexture = backgroundTexture;
        retryButtonOff = new Texture(Gdx.files.internal("images/buttons/button_off_retry.png"));
        retryButtonOn = new Texture(Gdx.files.internal("images/buttons/button_on_retry.png"));
        mainMenuButtonOff = new Texture(Gdx.files.internal("images/buttons/button_off_main_menu.png"));
        mainMenuButtonOn = new Texture(Gdx.files.internal("images/buttons/button_on_main_menu.png"));
        exitButtonOff = new Texture(Gdx.files.internal("images/buttons/button_off_exit.png"));
        exitButtonOn = new Texture(Gdx.files.internal("images/buttons/button_on_exit.png"));

        int retryButtonWidth = Gdx.graphics.getWidth() / 3;
        int retryButtonHeight = Gdx.graphics.getHeight() / 6;
        int buttonWidth = Gdx.graphics.getWidth() / 4;
        int buttonHeight = Gdx.graphics.getHeight() / 8;
        int buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        int retryButtonX = Gdx.graphics.getWidth() / 2 - retryButtonWidth / 2;
        int retryButtonY = Gdx.graphics.getHeight() / 2 - retryButtonHeight / 2;
        int mainMenuButtonY = (int)  (Gdx.graphics.getHeight() / 2 - buttonHeight / 2 - 1.5 * buttonHeight - 10);
        int exitButtonY = (int) (Gdx.graphics.getHeight() / 2 - buttonHeight / 2 - 2.5f * buttonHeight - 20);

        retryButton = new GameButton(retryButtonOff, retryButtonOn, retryButtonX, retryButtonY, retryButtonWidth, retryButtonHeight);
        mainMenuButton = new GameButton(mainMenuButtonOff, mainMenuButtonOn, buttonX, mainMenuButtonY, buttonWidth, buttonHeight);
        exitButton = new GameButton(exitButtonOff, exitButtonOn, buttonX, exitButtonY, buttonWidth, buttonHeight);

        screenManager = ScreenManager.getInstance();

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        retryButton.update(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        retryButton.draw(this.batch);
        mainMenuButton.update(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        mainMenuButton.draw(this.batch);
        exitButton.update(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        exitButton.draw(this.batch);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (retryButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                model.restartGame();
            } else if (mainMenuButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                model.stopGame();
            } else if (exitButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                Gdx.app.exit();
            }
        }

        batch.end();

        checkGameOver();

    }

    @Override
    public void resize(int width, int height) {
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
        backgroundTexture.dispose();
    }

    protected void checkGameOver() {

        if (GameModel.state == null) {
            // Handle the case where state is not initialized
            // For example, log an error or set a default state
            return;
        }

        if (!(GameModel.state == GameState.GAME_OVER)) {
            switch(GameModel.state) {
                case RUNNING -> {
                    screenManager.showGameScreen();
                }
                case MAIN_MENU -> {
                    screenManager.showStartScreen();
                }
            }
        }
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}