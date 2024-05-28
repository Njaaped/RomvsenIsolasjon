package inf112.skeleton.app.model.entity.powerup;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.alien.AlienOne;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.powerup.effects.GodModeEffect;
import inf112.skeleton.app.model.entity.powerup.effects.HealthEffect;
import inf112.skeleton.app.model.entity.powerup.effects.RayGunEffect;
import inf112.skeleton.app.model.entity.weapon.weapons.RayGun;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;


public class PowerUpTest {

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
    public void testCreation() {
        init();
        initApplication();
        PowerUp healthPacket = new PowerUp(new Vector2(0, 0), new HealthEffect(25), Media.healthPacket);
        assertNotNull(healthPacket);
        PowerUp rayGunPickup = new PowerUp(new Vector2(0, 0), new RayGunEffect(3), Media.raygun);
        assertNotNull(rayGunPickup);
        PowerUp star = new PowerUp(new Vector2(0, 0), new GodModeEffect(4), Media.star);
        assertNotNull(star);
    }

    @Test
    public void testInteract() {
        init();
        Player player = new Player(new Vector2(0, 0));
        PowerUp healthPacket = new PowerUp(new Vector2(0, 0), new HealthEffect(25), Media.healthPacket);
        PowerUp rayGunPickup = new PowerUp(new Vector2(0, 0), new RayGunEffect(3), Media.raygun);
        PowerUp star = new PowerUp(new Vector2(0, 0), new GodModeEffect(4), Media.star);
        AlienOne alien = new AlienOne(new Vector2(0, 0));
        rayGunPickup.interact(player, true);
        player.changeHealth(-20);
        assertTrue(player.getHealthPercentage() < 1f);
        healthPacket.interact(player, true);
        star.interact(player, true);
        Box2DWorldStatic.stepWorld(0.1f);
        assertTrue(player.getHealthPercentage() >= 1.0f);
        assertTrue(player.getWeapon() instanceof RayGun);
        player.interact(alien, true);
        // alien should be defeated
        boolean found = false;
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof AlienOne) {
                found = true;
            }
        }
        assertTrue(!found);
    }

    @Test
    public void testUpdate() {
        init();
        PowerUp healthPacket = new PowerUp(new Vector2(0, 0), new HealthEffect(25), Media.healthPacket);
        PowerUp rayGunPickup = new PowerUp(new Vector2(0, 0), new RayGunEffect(3), Media.raygun);
        PowerUp star = new PowerUp(new Vector2(0, 0), new GodModeEffect(4), Media.star);
        healthPacket.update(0.1f);
        rayGunPickup.update(0.1f);
        star.update(0.1f);
        // check if new position on y axis
        assertTrue(healthPacket.getPosition().y != 0);
        assertTrue(rayGunPickup.getPosition().y != 0);
        assertTrue(star.getPosition().y != 0);
    }

    @Test 
    public void testClass() {
        init();
        PowerUp healthPacket = new PowerUp(new Vector2(0, 0), new HealthEffect(25), Media.healthPacket);
        PowerUp rayGunPickup = new PowerUp(new Vector2(0, 0), new RayGunEffect(3), Media.raygun);
        PowerUp star = new PowerUp(new Vector2(0, 0), new GodModeEffect(4), Media.star);
        assertTrue(healthPacket.getOwnerType() == PowerUp.class);
        assertTrue(rayGunPickup.getOwnerType() == PowerUp.class);
        assertTrue(star.getOwnerType() == PowerUp.class);
    }

    
}
