package inf112.skeleton.app.model.mapfactories;

import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import inf112.skeleton.app.model.box2d.BodyFactory;

public class DefaultTileBodyCreator implements ITileBodyCreator {

    @Override
    public void createTileBody(Set<Body> bodies, TileType type, Vector2 position, int size) {
        Body tileBody =
        switch (type) {
            case WALL ->
                BodyFactory
                .createPhysicalBodyRectangle(size, size, position, BodyDef.BodyType.StaticBody);
            case HOLE ->
                BodyFactory
                .createSensorBodyRectangle(size, size, position, BodyDef.BodyType.StaticBody);
            default -> null;
        };

        if (tileBody != null) {
            Tile tile = new Tile(type);
            tileBody.setUserData(tile);
            bodies.add(tileBody);
        }

    }

}
