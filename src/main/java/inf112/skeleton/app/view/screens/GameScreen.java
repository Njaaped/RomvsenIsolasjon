package inf112.skeleton.app.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.weapon.Bullet;
import inf112.skeleton.app.model.gamestate.GameState;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.Tile;
import inf112.skeleton.app.model.mapfactories.TileType;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.view.ViewableModel;
import inf112.skeleton.app.view.ui.game_ui.UiRenderer;
import inf112.skeleton.app.view.ui.game_ui.healthbar.HealthDisplayDrawer;
import inf112.skeleton.app.view.ui.game_ui.minimap.MinimapDrawer;
import inf112.skeleton.app.view.ui.game_ui.score.ScoreDisplayDrawer;
import org.lwjgl.opengl.GL20;

public class GameScreen implements Screen {
    private final ViewableModel model;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Rectangle screenRectangle;

    private ShapeRenderer shapeRenderer;

    private MinimapDrawer minimapDrawer;
    private HealthDisplayDrawer healthDisplayDrawer;
    private ScoreDisplayDrawer scoreDisplayDrawer;
    private final Map map;

    private float transitionAlpha;
    private UiRenderer uiRenderer;


    public GameScreen(ViewableModel model) {
        this(model, new SpriteBatch());
    }

    public GameScreen(ViewableModel model, SpriteBatch batch) {
        this(model, batch, new ShapeRenderer());
    }

    public GameScreen(ViewableModel model, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this(model, batch, shapeRenderer, new OrthographicCamera());
    }

    public GameScreen(ViewableModel model, SpriteBatch batch, ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        this(model, batch, shapeRenderer, camera, new Rectangle());
    }

    public GameScreen(ViewableModel model,
                      SpriteBatch batch,
                      ShapeRenderer shapeRenderer,
                      OrthographicCamera camera,
                      Rectangle screenRectangle) {
        this.model = model;
        this.batch = batch;
        this.camera = camera;
        this.screenRectangle = screenRectangle;
        this.shapeRenderer = shapeRenderer;
        this.map = model.getMap();
        initComponents();
    }

    private void initComponents() {
        this.minimapDrawer = new MinimapDrawer(model.getMap());
        this.healthDisplayDrawer = new HealthDisplayDrawer(2.5f, 0.6f);
        this.scoreDisplayDrawer = new ScoreDisplayDrawer("fonts/ole_font/");
        this.uiRenderer = new UiRenderer(scoreDisplayDrawer, minimapDrawer, healthDisplayDrawer);

        setCameraZoom(1.5f);
        this.transitionAlpha = 1f;
    }

    private void setCameraZoom(float zoom) {
        this.camera.zoom = zoom;
    }

    private void setCameraPosition(Vector2 position, float zoom) {
        this.camera.position.set(position, zoom);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        render(delta, Box2DWorldStatic.getEntities());
    }

    public void render(float delta, Array<Entity> entities) {
        clearScreen();

        prepareCamera();

        int px = (int) model.getPlayerPosition().x;
        int py = (int) model.getPlayerPosition().y;

        drawTiles(px, py);
        drawEntities(entities, px, py);

        this.uiRenderer.drawUi(batch, camera, (GameModel) model);

        batch.end();

        handleGameFlow();
    }

    private void drawTiles(int px, int py) {

        // render only 10 closest tiles to the player
        for (int row = py - 10; row < py + 10; row++) {
            for (int col = px - 10; col < px + 10; col++) {
                if (row < 0 || col < 0 || row >= map.getHeight() || col >= map.getWidth()) {
                    continue;
                }
                Tile tile = map.getTile(row, col);
                Vector2 tilePosition = map.convertToWorldPosition(row, col);
                int tileSize = map.getTileSize();
                batch.draw(tile.getSecondaryTexture(), tilePosition.x, tilePosition.y, tileSize, tileSize);
                if (tile.getPrimaryTexture() != null) {
                    batch.draw(tile.getPrimaryTexture(), tilePosition.x, tilePosition.y, tileSize, tileSize);
                }
                if (tile.getTiletype() == TileType.PLAYER_SPAWN) {
                    batch.draw(Media.green, tilePosition.x, tilePosition.y, tileSize, tileSize);
                }
            }
        }
    }

    private void drawEntities(Array<Entity> entities, int px, int py) {
        entities.sort((o1, o2) -> (int) Math.signum(o2.getPosition().y - o1.getPosition().y));
        for (Entity entity : entities) {
            Vector2 entityPosition = entity.getPosition();
            if (entityPosition.x < px - 10 || entityPosition.x > px + 10 || entityPosition.y < py - 10 || entityPosition.y > py + 10) {
                continue;
            }
            if (!(entity instanceof Bullet)) {
                batch.draw(entity.getPrimaryTexture(), entity.getPosition().x - entity.getWidth() / 2, entity.getPosition().y - entity.getHeight() / 2, entity.getWidth(), entity.getHeight());
            } else {
                batch.draw(
                    entity.getPrimaryTexture(),
                    entity.getPosition().x - Bullet.WIDTH / 2f, entity.getPosition().y - Bullet.HEIGHT / 2f, // Texture position
                    Bullet.WIDTH / 2f, Bullet.HEIGHT / 2f, // Origin point (center of the texture)
                    Bullet.WIDTH, Bullet.HEIGHT, // Width and height of the texture
                    1f, 1f, // Scale on x and y (no scaling)
                    (Bullet.getDirection(entity) - 90) % 360, // Rotation angle in degrees
                    0, 0, // Starting point of the texture in the Texture (useful for TextureRegion)
                    entity.getPrimaryTexture().getWidth(), entity.getPrimaryTexture().getHeight(), // The width and height of the area from the Texture to draw
                    false, false // Whether to flip the sprite horizontally or vertically
                );
            }
        }
    }

    private void clearScreen(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    private void prepareCamera() {
        setCameraPosition(model.getPlayerPosition(), .4f);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }


    private void handleGameFlow() {
        transitionAndPauseLogic();
        checkGameOver();
        model.drawHitboxes(camera);
    }


    /**
     * get the camera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }


    @Override
    public void resize(int width, int height) {
        screenRectangle.width = width;
		    screenRectangle.height = height;
        float ratio = (0f + Gdx.graphics.getWidth()) / Gdx.graphics.getHeight();

        camera.viewportWidth = ratio * 5;
        camera.viewportHeight = 5;

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // Does nothing
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * This method is used to create a transition effect between screens.
     * Also checks if the game is paused and renders the pause menu if it is.
     */

    private void transitionAndPauseLogic() {
        if (transitionAlpha > 0f) {
            transitionAlpha = ScreenManager.transitionEffect1(transitionAlpha, 1, 0, 1, shapeRenderer);
            if (transitionAlpha < 0.01f) {
                GameModel.state = GameState.RUNNING;
            }
        } else {
            if (GameModel.state == GameState.PAUSED) {
                ScreenManager.pauseMenu(shapeRenderer);
            }
        }
    }

    /**
     * This method checks if the game is over and shows the game over screen if it is.
     */

    private void checkGameOver() {
        if (GameModel.state == GameState.GAME_OVER) {
            ScreenManager.getInstance().showGameOverScreen();
        }
    }

}
