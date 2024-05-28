package inf112.skeleton.app.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.model.entity.alien.AlienOne;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.entity.weapon.Bullet;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;

public class EntityTest {

    private static HeadlessApplication headlessApplication;
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
        Map map = getMockMap();
        Box2DWorldStatic.startBox2DWorld();
        Box2DWorldStatic.loadMap(map, new DefaultTileBodyCreator());
    }

    @Test
    public void testPlayer() {
        initApplication();
        init();
        Player player = new Player(new Vector2(2, 2));
        assertNotNull(player);
    }

    @Test
    public void testAlien() {
        initApplication();
        init();
        AlienOne alien = new AlienOne(new Vector2(2, 2));
        assertNotNull(alien);
    }

    @Test 
    public void setPositionTest() {
        init();
        Entity player = new Player(new Vector2(2, 2));
        player.setPosition(new Vector2(2, 3));
      assertEquals("actual position: " + player.getPosition(), player.getPosition(), new Vector2(2, 3));
    }

    @Test
    public void getWidthTest() {
        init();
        Entity player = new Player(new Vector2(2, 2));
      assertEquals(1, player.getWidth(), 0.0);
    }

    @Test
    public void getHeightTest() {
        init();
        Entity player = new Player(new Vector2(2, 2));
      assertEquals(1, player.getHeight(), 0.0);
    }

    @Test
    public void getBodyTest() {
        init();
        Entity player = new Player(new Vector2(2, 2));
      assertNotNull(player.getPhysicalBody());
        AlienOne alien = new AlienOne(new Vector2(2, 2));
      assertNotNull(alien.getPhysicalBody());
        // shold exist for bullet
        Bullet bullet = new Bullet(new Vector2(2, 2), new Vector2(1, 1), 0.1f, 1, 0.1f, Player.class);
      assertNull(bullet.getPhysicalBody());
    }

    @Test
    public void getSensorBodyTest() {
        init();
        Entity player = new Player(new Vector2(2, 2));
        // should be null for player and alien
      assertNull(player.getSensorBody());
        AlienOne alien = new AlienOne(new Vector2(2, 2));
      assertNull(alien.getSensorBody());
        // shold exist for bullet
        Bullet bullet = new Bullet(new Vector2(2, 2), new Vector2(1, 1), 0.1f, 1, 0.1f, Player.class);
      assertNotNull(bullet.getSensorBody());
    }

    @Test
    public void hashCodeTest() {
        init();
        Entity player = new Player(new Vector2(2, 2));
        assertTrue(player.hashCode() != 0);
    }




    
    
}
