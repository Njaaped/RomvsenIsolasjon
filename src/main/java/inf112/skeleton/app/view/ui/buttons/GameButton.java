package inf112.skeleton.app.view.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameButton {
    private final Texture normalTexture;
    private final Texture hoverTexture;
    private final Rectangle bounds;
    private boolean isHovering;

    public GameButton(Texture normalTexture, Texture hoverTexture, Rectangle bounds) {
        this.normalTexture = normalTexture;
        this.hoverTexture = hoverTexture;
        this.bounds = bounds;
    }

    public GameButton(Texture normalTexture, Texture hoverTexture, float x, float y, float width, float height) {
        this(normalTexture, hoverTexture, new Rectangle(x, y, width, height));
    }

    public void setHovering(boolean isHovering) {
        this.isHovering = isHovering;
    }

    public void update(float mouseX, float mouseY) {
        isHovering = bounds.contains(mouseX, mouseY);
    }

    public void draw(SpriteBatch batch) {
        Texture activeTexture = normalTexture;
        if (isHovering) {
            activeTexture = hoverTexture;
        }
        batch.draw(activeTexture, bounds.x, bounds.y, bounds.width, bounds.height);

    }

    public void dispose() {
        normalTexture.dispose();
        hoverTexture.dispose();
    }

    public void setBounds(Rectangle rectangle) {
        bounds.set(rectangle);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
