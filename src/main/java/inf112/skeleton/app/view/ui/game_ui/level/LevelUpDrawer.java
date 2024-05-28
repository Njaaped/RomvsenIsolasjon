package inf112.skeleton.app.view.ui.game_ui.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelUpDrawer {
    private final Texture[] digitTextures = new Texture[10];
    private final Texture levelLabelTexture;

    public LevelUpDrawer() {
        for (int i = 0; i < 10; i++) {
            digitTextures[i] = new Texture(Gdx.files.internal("fonts/ole_font/" + i + ".png"));
        }
        levelLabelTexture = new Texture(Gdx.files.internal("fonts/ole_font/" + "Level.png"));
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera, int level) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        String levelStr = String.valueOf(level);
        float offsetX = (float) width / 5;  // Label width
        float digitWidth = (float) width / 25;


        float levelX = (float) width / 2 - offsetX / 2 - levelStr.length() * digitWidth;
        float levelY = (float) height / 2;

        batch.draw(levelLabelTexture, levelX, levelY, (float) width / 5, (float) height / 10);

        for (char digit : levelStr.toCharArray()) {
            batch.draw(digitTextures[Character.getNumericValue(digit)], levelX + offsetX, levelY, (float) width / 10, (float) height / 10);
            offsetX += digitWidth + 2 * digitWidth / 3;
        }
    }

    public void dispose() {
        for (Texture texture : digitTextures) {
            texture.dispose();
        }
        levelLabelTexture.dispose();
    }
}
