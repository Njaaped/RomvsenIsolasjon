
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
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.weapon.weapons.Hand;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;


public class HandTest {


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
    public void testHand() {
        init();
        initApplication();
        Player player = new Player(new Vector2(2, 2));
        Hand hand = new Hand(player.getPhysicalBody(), Player.class);
        assertNotNull(hand);
    }

    @Test
    public void testAttackAndReduceCoolDown() {
        //should create a strike
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        Hand hand = new Hand(player.getPhysicalBody(), Player.class);
        hand.attack(new Vector2(1,0));
        for (int i = 0; i < 100; i++) {
            hand.reduceCooldown();
        }
        hand.attack(new Vector2(1,0));
        Strike strike = null;
        Array<Body> bodies = new Array<Body>();
        Box2DWorldStatic.world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() instanceof Strike) {
                strike = (Strike) body.getUserData();
            }
        }
        assertNotNull(strike);
    }

    
}
