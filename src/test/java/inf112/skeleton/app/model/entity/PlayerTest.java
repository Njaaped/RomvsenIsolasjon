package inf112.skeleton.app.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.weapon.Bullet;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;

public class PlayerTest {

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
        // Test if player is created
        init();
    }

    @Test
    public void setMovementDirectionTest() {
        // Test if player can move
        init();
        Player player = new Player(new Vector2(0,0));
        player.setMovementDirection(new Vector2(1,1));
        player.update(0.1f);
        System.out.println(player.getPosition());
        assertTrue( "player did not move new pos: " + player.getPosition(), player.getPosition().x != 0f);
        assertTrue("player did not move new pos: " + player.getPosition(), player.getPosition().y != 0f);
    }

    @Test
    public void testMoveUpdate2() {
        // Test if player can move
        init();
        Player player = new Player(new Vector2(0,0));
        player.setMovementDirection(new Vector2(-1,-1));
        player.update(0.1f);
        System.out.println(player.getPosition());
        assertTrue( "player did not move new pos: " + player.getPosition(), player.getPosition().x != 0f);
        assertTrue("player did not move new pos: " + player.getPosition(), player.getPosition().y != 0f);
    }

    @Test
    public void testSpeedIncrease() {
        // Test if player can move faster
        init();
        Player player = new Player(new Vector2(0,0));
        player.setMovementDirection(new Vector2(1,1));
        player.update(0.1f);
        double speed = player.getSpeed();
        player.setSprintState(true);
        player.update(0.1f);
        assertTrue("player did not increase speed", player.getSpeed() > speed);
    }

    @Test
    public void testMoveMentDirection() {
        // Test if player can move in a specific direction
        init();
        Player player = new Player(new Vector2(0,0));
        player.setMovementDirection(new Vector2(1,1));
        Vector2 direction = player.getMovementDirection();
        player.update(0.1f);
        player.setMovementDirection(new Vector2(-1,-1));
        player.update(0.1f);
        assertTrue("player did not change direction", direction.x != player.getMovementDirection().x);
    }


    @Test
    public void testSetAttacking() {
        // Test if player can attack
        init();
        initApplication();
        Player player = new Player(new Vector2(0,0));
        player.setAttacking(false, new Vector2(0,0));
        // bullet should spawn next update
        player.setMovementDirection(new Vector2(1,1));
        Box2DWorldStatic.stepWorld(0.1f);
        player.setAttacking(true, new Vector2(1,1));
        Box2DWorldStatic.stepWorld(0.1f);
        Entity entity = (Entity) player;
        entity.update(.1f);
        Array<Body> bodies = new Array<>();
        Box2DWorldStatic.world.getBodies(bodies);
        boolean bulletFound = false;
        while (!bulletFound) {
            for (Body body : bodies) {
                if (body.getUserData() instanceof Bullet) {
                    bulletFound = true;
                    break;
                }
            }
            player.setAttacking(true, new Vector2(1,1));
            Box2DWorldStatic.stepWorld(0.1f);
            Box2DWorldStatic.world.getBodies(bodies);
        }
        assertTrue("player did not attack", bulletFound);
    }

    @Test
    public void getHealthTest() {
        // Test if player can get health
        init();
        Player player = new Player(new Vector2(0,0));
        assertTrue("player did not get health", player.getHealthPercentage() == 1);
    }

    @Test
    public void playerDamageTest() {
        // Test if player can take damage
        init();
        Player player = new Player(new Vector2(0,0));
        player.changeHealth(-10);
        assertTrue("player did not take damage", player.getHealthPercentage() < 1);
    }

    @Test
    public void ownerTypeTest() {
        // Test if player can get owner type
        init();
        Player player = new Player(new Vector2(0,0));
        assertTrue("player did not get owner type", player.getOwnerType().equals(Player.class));
    }



}
