package inf112.skeleton.app.view.ui.game_ui.healthbar;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.skeleton.app.view.Media;

public class HealthDisplayDrawer {
    private final float width;
    private final float height;

    public HealthDisplayDrawer(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera, float healthPercentage) {

        if (healthPercentage < 0) {
            healthPercentage = 0;
        } else if (healthPercentage > 1) {
            healthPercentage = 1;
        }

        float cameraX = camera.position.x - camera.zoom * camera.viewportWidth / 2 + 0.25f;
        float cameraY = camera.position.y - camera.zoom * camera.viewportHeight / 2 + 0.25f;

        TextureRegion backFace = new TextureRegion(Media.gray);
        TextureRegion health = new TextureRegion(Media.green);

        batch.draw(backFace, cameraX, cameraY, this.width, this.height);
        float healthWidth = this.width * 0.92f * healthPercentage;
        batch.draw(health, cameraX + this.width * 0.04f, cameraY + this.height * 0.1f, healthWidth, this.height * 0.8f);
    }
}
