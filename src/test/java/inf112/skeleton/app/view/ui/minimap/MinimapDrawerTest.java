package inf112.skeleton.app.view.ui.minimap;

import static org.junit.Assert.assertNotNull;

import inf112.skeleton.app.view.ui.game_ui.minimap.MinimapDrawer;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.TileType;
import inf112.skeleton.app.model.mapfactories.Tile;

public class MinimapDrawerTest {

    private Map getMap() {
        
        Array<Array<Tile>> map = new Array<>(true, 10);
        for (int i = 0; i < 10; i++) {
            Array<Tile> mid = new Array<>(true, 10);
            for (int j = 0; j < 10; j++) {
                Tile fakeTile = new Tile(TileType.FLOOR);
                mid.add(fakeTile);
            }
            map.add(mid);
        }
        Map newmap = new Map(map);
        return newmap;
    }

    @Test
    public void testDraw() {
        Map map = this.getMap();
        MinimapDrawer minimapDrawer = new MinimapDrawer(map);
        minimapDrawer.draw(null, new OrthographicCamera(), new Vector2(0,0), new Array<Entity>(), 0);
        assertNotNull(minimapDrawer);
    }



    
}
