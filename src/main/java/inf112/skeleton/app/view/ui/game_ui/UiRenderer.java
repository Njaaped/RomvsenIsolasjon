package inf112.skeleton.app.view.ui.game_ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.view.ui.game_ui.healthbar.HealthDisplayDrawer;
import inf112.skeleton.app.view.ui.game_ui.minimap.MinimapDrawer;
import inf112.skeleton.app.view.ui.game_ui.score.ScoreDisplayDrawer;

public class UiRenderer {
    private final ScoreDisplayDrawer scoreDisplayDrawer;
    private final MinimapDrawer minimapDrawer;
    private final HealthDisplayDrawer healthDisplayDrawer;

    public UiRenderer(ScoreDisplayDrawer scoreDisplay, MinimapDrawer minimap, HealthDisplayDrawer healthDisplay) {
        this.scoreDisplayDrawer = scoreDisplay;
        this.minimapDrawer = minimap;
        this.healthDisplayDrawer = healthDisplay;
    }

    public void drawUi(SpriteBatch batch, OrthographicCamera camera, GameModel model) {
        scoreDisplayDrawer.draw(batch, camera, model.getScore());
        minimapDrawer.draw(batch, camera, model.getPlayerPosition(), model.getEntities(), 30);
        healthDisplayDrawer.draw(batch, camera, model.getPlayerHealthPercentage());
    }
}
