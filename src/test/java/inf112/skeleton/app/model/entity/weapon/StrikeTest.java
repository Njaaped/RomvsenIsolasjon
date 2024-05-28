package inf112.skeleton.app.model.entity.weapon;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import inf112.skeleton.app.model.entity.alien.AlienOne;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;


public class StrikeTest {

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
    public void testStrike() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        Body body = player.getPhysicalBody();
        Strike strike = new Strike(body, new Vector2(), new Vector2(), 0f, 0, 0f, Player.class);
        assertNotNull(strike);
    }


    @Test
    public void gettersTest() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        Body body = player.getPhysicalBody();
        Strike strike = new Strike(body, new Vector2(), new Vector2(), 0f, 0, 0f, Player.class);
        assertTrue(strike.getPosition() != null);
        assertTrue(strike.getOwnerType() != null);
    }

    @Test
    public void interactTest() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        Body body = player.getPhysicalBody();
        Strike strike = new Strike(body, new Vector2(), new Vector2(), 0f, 0, 0f, Player.class);
        AlienOne alien = new AlienOne(new Vector2(2, 2));
        AlienOne newalien = null;
        // check if alien exists
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof AlienOne) {
                newalien = (AlienOne) entity;
            }
        }
        assertNotNull(newalien);
        strike.interact(alien, true);
        AlienOne newalien2 = null;
        // check if alien is dead
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof AlienOne) {
                newalien2 = (AlienOne) entity;
            }
        }
        assertTrue("alien should not exists, " + newalien2 , newalien2 == null);
    }
    
    
}
