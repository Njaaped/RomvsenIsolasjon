package inf112.skeleton.app.model.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.model.entity.alien.AlienBomber;
import inf112.skeleton.app.model.entity.alien.AlienOne;
import inf112.skeleton.app.model.entity.alien.AlienTwo;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.MapEntry;
import inf112.skeleton.app.model.mapfactories.TileType;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;

public class AlienTest {

    private static HeadlessApplication headlessApplication;
    private Map map;
    /**
     * Initialise a test headless application.
     */
    public static void initApplication() {
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            ApplicationListener listener = new ApplicationListener() {
                @Override
                public void create() {
                }

                @Override
                public void resize(int width, int height) {
                }

                @Override
                public void render() {
                }

                @Override
                public void pause() {
                }

                @Override
                public void resume() {
                }

                @Override
                public void dispose() {
                }
            };
            headlessApplication = new HeadlessApplication(listener, config);
            Gdx.graphics.setForegroundFPS(60);
            assertNotNull(Gdx.files);
        }
    }

    private Map getMockMap() {
        InsaneMapFactory mapFactory = new InsaneMapFactory(123);
        Map map = mapFactory.generate(100, 100, 1);
        return map;
    }


    private void init() {
        this.map = getMockMap();
        Box2DWorldStatic.startBox2DWorld();
        Box2DWorldStatic.loadMap(map, new DefaultTileBodyCreator());        
    }

    @Test
    public void testAlien() {
        // Test if alien is created
        init();
    }

    @Test
    public void testMoveUpdateCantSeePlayerAlienOne() {
        // Test if alien can move
        init();
        Player player = new Player(new Vector2(map.getSpawnPoint()[0], map.getSpawnPoint()[1]));
        AlienOne alienOne = new AlienOne(new Vector2(2, 2));
        Vector2 pos1 = alienOne.getPosition();
        alienOne.update(0.1f);
        Vector2 pos2 = alienOne.getPosition();
        assert(pos1 == pos2);
    }

    @Test
    public void testMoveUpdateCantSeePlayerAlienBomber() {
        // Test if alien can move
        init();
        // get random tile
        Player player = new Player(new Vector2(map.getSpawnPoint()[0], map.getSpawnPoint()[1]));
        for (MapEntry tile : map) {
            if (tile.tileType() == TileType.FLOOR) {
                AlienBomber alienBomber = new AlienBomber(new Vector2(tile.col(), tile.row()), 49);
                Vector2 pos1 = alienBomber.getPosition();
                alienBomber.update(0.1f);
                Vector2 pos2 = alienBomber.getPosition();
                assert(pos1 == pos2);
                break;
            }
        }
       
    }

    @Test
    public void testMoveUpdateCantSeePlayerAlienTwo() {
        // Test if alien can move
        init();
        // get random tile
        Player player = new Player(new Vector2(map.getSpawnPoint()[0], map.getSpawnPoint()[1]));
        for (MapEntry tile : map) {
            if (tile.tileType() == TileType.FLOOR) {
                AlienTwo alienTwo = new AlienTwo(new Vector2(tile.col(), tile.row()));
                Vector2 pos1 = alienTwo.getPosition();
                alienTwo.update(0.1f);
                Vector2 pos2 = alienTwo.getPosition();
                assert(pos1 == pos2);
                break;
            }
        }
       
    }

    @Test
    public void testMoveUpdateCanSeePlayerAlienOne() {
        // Test if alien can move
        init();
        initApplication();
        AlienOne alienOne = new AlienOne(new Vector2(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1]));
        Player player = new Player(new Vector2(map.getSpawnPoint()[0], map.getSpawnPoint()[1]));
        
        Vector2 pos1 = alienOne.getPosition().cpy();
        alienOne.update(.1f);
        alienOne.update(.1f);
        alienOne.update(.1f);

        Vector2 pos2 = alienOne.getPosition();
        assertTrue(Box2DWorldStatic.getPlayer() == player);
        assertTrue( "AlienOne did not move", pos1 != pos2);
    }

    @Test
    public void testCanSeePlayerAlienBomber() {
        // Test if alien can move
        init();
        initApplication();
        AlienBomber alienBomber = new AlienBomber(new Vector2(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1]), 49);
        Player player = new Player(new Vector2(map.getSpawnPoint()[0], map.getSpawnPoint()[1]));
        
        Vector2 pos1 = alienBomber.getPosition().cpy();
        alienBomber.update(.1f);
        alienBomber.update(.1f);
        alienBomber.update(.1f);

        Vector2 pos2 = alienBomber.getPosition();
        assertTrue(Box2DWorldStatic.getPlayer() == player);
        assertTrue( "AlienTwo did not move", pos1 != pos2);
    }


    @Test
    public void testMoveUpdateCanSeePlayerAlienTwo() {
        // Test if alien can move
        init();
        initApplication();
        AlienTwo alienTwo = new AlienTwo(new Vector2(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1]));
        Player player = new Player(new Vector2(map.getSpawnPoint()[0], map.getSpawnPoint()[1]));
        
        Vector2 pos1 = alienTwo.getPosition().cpy();
        alienTwo.update(.1f);
        alienTwo.update(.1f);
        alienTwo.update(.1f);

        Vector2 pos2 = alienTwo.getPosition();
        assertTrue(Box2DWorldStatic.getPlayer() == player);
        assertTrue( "AlienTwo did not move", pos1 != pos2);
    }


    @Test
    public void testNoMoveCantSeeAlienOne() {
        // Test if alien can move faster
        init();
        initApplication();
        AlienOne alienOne = new AlienOne(new Vector2(0,0));
        Player player = new Player(new Vector2(map.getSpawnPoint()[0],map.getSpawnPoint()[1]));
        alienOne.update(0.1f);
        Vector2 pos1 = alienOne.getPosition();
        alienOne.update(0.1f);
        Vector2 pos2 = alienOne.getPosition();
        assertTrue( "AlienOne moved", pos1 == pos2);
    }

    @Test
    public void testNoMoveCantSeeAlienBomber() {
        // Test if alien can move faster
        init();
        initApplication();
        AlienBomber alienBomber = new AlienBomber(new Vector2(0,0), 49);
        Player player = new Player(new Vector2(map.getSpawnPoint()[0],map.getSpawnPoint()[1]));
        alienBomber.update(0.1f);
        Vector2 pos1 = alienBomber.getPosition();
        alienBomber.update(0.1f);
        Vector2 pos2 = alienBomber.getPosition();
        assertTrue( "AlienOne moved", pos1 == pos2);
    }

    @Test
    public void testNoMoveCantSeeAlienTwo() {
        // Test if alien can move faster
        init();
        initApplication();
        AlienTwo alienTwo = new AlienTwo(new Vector2(0,0));
        Player player = new Player(new Vector2(map.getSpawnPoint()[0],map.getSpawnPoint()[1]));
        alienTwo.update(0.1f);
        Vector2 pos1 = alienTwo.getPosition();
        alienTwo.update(0.1f);
        Vector2 pos2 = alienTwo.getPosition();
        assertTrue( "AlienTwo moved", pos1 == pos2);
    }

    @Test
    public void testAlienOneSeenCantSeePath() {
        // Test if alien can move faster
        init();
        initApplication();
        AlienOne alienOne = new AlienOne(new Vector2(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1]));
        Player player = new Player(new Vector2(map.getSpawnPoint()[0],map.getSpawnPoint()[1]));
        int thresh = 10;
        for (MapEntry tile : map) {
            if (tile.tileType() == TileType.FLOOR) {
                thresh--;
                if (thresh == 0) {
                    alienOne.setPosition(new Vector2(tile.col(), tile.row()));
                    break;
                }
            }
        }
        Vector2 pos1 = alienOne.getPosition().cpy();
        alienOne.update(0.1f);
        alienOne.update(0.1f);
        alienOne.update(0.1f);
        Vector2 pos2 = alienOne.getPosition();
        assertTrue(Box2DWorldStatic.getPlayer() == player);
        assertTrue( "AlienOne did not move", pos1 != pos2);
    }

    @Test
    public void testAlienBomberSeenCantSeePath() {
        // Test if alien can move faster
        init();
        initApplication();
        AlienBomber alienBomber = new AlienBomber(new Vector2(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1]), 49);
        Player player = new Player(new Vector2(map.getSpawnPoint()[0],map.getSpawnPoint()[1]));
        int thresh = 10;
        for (MapEntry tile : map) {
            if (tile.tileType() == TileType.FLOOR) {
                thresh--;
                if (thresh == 0) {
                    alienBomber.setPosition(new Vector2(tile.col(), tile.row()));
                    break;
                }
            }
        }
        Vector2 pos1 = alienBomber.getPosition().cpy();
        alienBomber.update(0.1f);
        alienBomber.update(0.1f);
        alienBomber.update(0.1f);
        Vector2 pos2 = alienBomber.getPosition();
        assertTrue(Box2DWorldStatic.getPlayer() == player);
        assertTrue( "AlienOne did not move", pos1 != pos2);
    }


    @Test
    public void testAlienTwoSeenCantSeePath() {
        // Test if alien can move faster
        init();
        initApplication();
        AlienTwo alienTwo = new AlienTwo(new Vector2(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1]));
        Player player = new Player(new Vector2(map.getSpawnPoint()[0],map.getSpawnPoint()[1]));
        int thresh = 10;
        for (MapEntry tile : map) {
            if (tile.tileType() == TileType.FLOOR) {
                thresh--;
                if (thresh == 0) {
                    alienTwo.setPosition(new Vector2(tile.col(), tile.row()));
                    break;
                }
            }
        }
        Vector2 pos1 = alienTwo.getPosition().cpy();
        alienTwo.update(0.1f);
        alienTwo.update(0.1f);
        alienTwo.update(0.1f);
        Vector2 pos2 = alienTwo.getPosition();
        assertTrue(Box2DWorldStatic.getPlayer() == player);
        assertTrue( "alienTwo did not move", pos1 != pos2);
    }

    @Test
    public void damageTestAlienOne() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        AlienOne alienOne = new AlienOne(new Vector2(0,0));
        alienOne.changeHealth(-1);
        boolean entityinworld = false;
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof AlienOne) {
                if (entity == alienOne) {
                    entityinworld = true;
                }
            }
        }
        assertTrue("AlienOne didnt die", !entityinworld);
    }


    @Test
    public void damageTestAlienBomber() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        AlienBomber alienBomber = new AlienBomber(new Vector2(0,0), 49);
        alienBomber.changeHealth(-1);
        boolean entityinworld = false;
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof AlienOne) {
                if (entity == alienBomber) {
                    entityinworld = true;
                }
            }
        }
        assertTrue("AlienOne didnt die", !entityinworld);
    }


    @Test
    public void damageTestAlienTwo() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        AlienTwo alienTwo = new AlienTwo(new Vector2(0,0));
        alienTwo.changeHealth(-1);
        boolean entityinworld = false;
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof AlienTwo) {
                if (entity == alienTwo) {
                    entityinworld = true;
                }
            }
        }
        assertTrue("AlienTwo didnt die", !entityinworld);
    }

    @Test
    public void testOwnerType() {
        init();
        AlienOne alienOne = new AlienOne(new Vector2(0,0));
        assertTrue(alienOne.getOwnerType() == AlienOne.class);
        AlienTwo alienTwo = new AlienTwo(new Vector2(0,0));
        assertTrue(alienTwo.getOwnerType() == AlienTwo.class);
        AlienBomber alienBomber = new AlienBomber(new Vector2(0,0), 49);
        assertTrue(alienBomber.getOwnerType() == AlienBomber.class);
    }

    @Test
    public void testAlienBomberBlowUpPlayer() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(0, 0));
        AlienBomber alienBomber = new AlienBomber(new Vector2(0,0), 49);
        alienBomber.interact(player, true);
        assertTrue("Player didnt loose life", player.getHealthPercentage() < 1f);
    }

    
}
