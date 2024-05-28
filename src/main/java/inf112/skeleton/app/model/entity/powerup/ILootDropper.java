package inf112.skeleton.app.model.entity.powerup;

import com.badlogic.gdx.math.Vector2;

public interface ILootDropper {
    /**
     * Drops an item at the given position
     * @param position position to drop item
     */
    public void dropItem(Vector2 position);
}
