package inf112.skeleton.app.view.ui.game_ui.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreDisplayDrawer {
    private final Texture[] digitTextures = new Texture[10];
    private final Texture scoreLabelTexture;

    public ScoreDisplayDrawer(String fontPath) {
        for (int i = 0; i < 10; i++) {
            digitTextures[i] = new Texture(Gdx.files.internal(fontPath + i + ".png"));
        }
        scoreLabelTexture = new Texture(Gdx.files.internal(fontPath + "Score.png"));
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera, int score) {
        String scoreStr = String.valueOf(score);
        float offsetX = 1.6f;  // Label width

        float scoreX = camera.position.x - camera.zoom * camera.viewportWidth / 2 + 0.25f;
        float scoreY = camera.position.y + camera.zoom * camera.viewportHeight / 2 - 0.75f;

        batch.draw(scoreLabelTexture, scoreX, scoreY, 1.5f, 0.5f);

        for (char digit : scoreStr.toCharArray()) {
            batch.draw(digitTextures[Character.getNumericValue(digit)], scoreX + offsetX, scoreY, 0.25f, 0.5f);
            offsetX += 0.25f;
        }
    }

    public void dispose() {
        for (Texture texture : digitTextures) {
            texture.dispose();
        }
        scoreLabelTexture.dispose();
    }
}
