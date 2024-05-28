package inf112.skeleton.app.model.mapfactories;

import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface ITileBodyCreator {
    /**
     * Create a body for the tile, add it to the world and store a refrence in the bodies set.
     * @param bodies A set to store body refrences
     * @param type Type of the tile
     * @param position Position of the center of the tile
     * @param size Width and height of the tile
     */
    void createTileBody(Set<Body> bodies, TileType type, Vector2 position, int size);
}
