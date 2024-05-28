package inf112.skeleton.app.model.mapfactory;

import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.TileType;
import inf112.skeleton.app.model.mapfactories.maputils.IntegerToTile;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.view.Media;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;



public class InsaneMapFactoryTest {

    private Map createMap() {
        Box2DWorldStatic.startBox2DWorld();
        InsaneMapFactory mapFactory = new InsaneMapFactory();
        return mapFactory.generate(100, 100, 5);
    }

    private Map createMap(int seed) {
        Box2DWorldStatic.startBox2DWorld();
        InsaneMapFactory mapFactory = new InsaneMapFactory(seed);
        return mapFactory.generate(100, 100, 5);
    }

    private Map createMap(int seed, int difficulty) {
        Box2DWorldStatic.startBox2DWorld();
        InsaneMapFactory mapFactory = new InsaneMapFactory(seed);
        return mapFactory.generate(100, 100, difficulty);
    }

    private Map createMap(int seed, int width, int height, int difficulty) {
        Box2DWorldStatic.startBox2DWorld();
        InsaneMapFactory mapFactory = new InsaneMapFactory(seed);
        return mapFactory.generate(width, height, difficulty);
    }

    private boolean equal(Map map1, Map map2) {
        for (int i = 0; i < map1.getTiles().size; i++) {
            for (int j = 0; j < map1.getTiles().get(i).size; j++) {
                if (map1.getTiles().get(i).get(j).getTiletype() != map2.getTiles().get(i).get(j).getTiletype()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void testMapCreation() {
        Map map = createMap();
        assertNotNull(map);
    }

    @Test
    public void testMapSize(){
        Map map = createMap();
        assertEquals(100, map.getTiles().size);
        assertEquals(100, map.getTiles().get(0).size);
    }

    @Test
    public void testMapSeed(){
        Map map1 = createMap(123);
        Map map2 = createMap(123);
        assert(equal(map1, map2));
    }

    @Test
    public void testMapSeedDifferent(){
        Map map1 = createMap(123);
        Map map2 = createMap(124);
        assert(!equal(map1, map2));
    }

    @Test
    public void testMapDifficulty(){
        Map map1 = createMap(123, 5);
        Map map2 = createMap(123, 5);
        assert(equal(map1, map2));
    }

    @Test
    public void testMapDifficultyDifferent(){
        Map map1 = createMap(123, 5);
        Map map2 = createMap(123, 6);
        assert(!equal(map1, map2));
    }

    @Test
    public void testMapSizeCustom(){
        Map map = createMap(123, 50, 50, 5);
        assertEquals(50, map.getTiles().size);
        assertEquals(50, map.getTiles().get(0).size);
    }

    @Test
    public void testMapSizeCustomSeed(){
        Map map1 = createMap(123, 50, 50, 5);
        Map map2 = createMap(123, 50, 50, 5);
        assert(equal(map1, map2));
    }

    @Test
    public void randomSeedDifferent(){
        Map map1 = createMap();
        Map map2 = createMap();
        assert(!equal(map1, map2));
    }

    @Test
    public void intToTileTest() {
        assert(IntegerToTile.intToTile(0) == TileType.EMPTY);
        assert(IntegerToTile.intToTile(1) == TileType.FLOOR);
        assert(IntegerToTile.intToTile(2) == TileType.WALL);
        assert(IntegerToTile.intToTile(4) == TileType.HOLE);
        assert(IntegerToTile.intToTile(8) == TileType.ENEMY_SPAWN);
        assert(IntegerToTile.intToTile(9) == TileType.PLAYER_SPAWN);
    }

    @Test
    public void intToSizeTest() {
        assert(IntegerToTile.intToSize(0) == 1);
        assert(IntegerToTile.intToSize(1) == 1);
        assert(IntegerToTile.intToSize(2) == 1);
        assert(IntegerToTile.intToSize(4) == 1);
        assert(IntegerToTile.intToSize(8) == 1);
        assert(IntegerToTile.intToSize(9) == 1);
    }

    @Test
    public void intToTextureTest() {
        Media.ground = null;
        Media.space1 = null;
        Media.space5 = null;
        Media.wallTop = null;
        assertNull(IntegerToTile.intToTexture(0));
        assertNull(IntegerToTile.intToTexture(1));
        assertNull(IntegerToTile.intToTexture(2));
        assertNull(IntegerToTile.intToTexture(4));
        assertNull(IntegerToTile.intToTexture(8));
        assertNull(IntegerToTile.intToTexture(9));
    }


}


