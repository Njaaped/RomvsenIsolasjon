
package inf112.skeleton.app.model.entity.weapon;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.weapon.weapons.RayGun;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;


public class RayGunTest {
    
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
    public void testrayGun() {
        initApplication();
        init();
        Player player = new Player(new Vector2(2, 2));
        RayGun alienrayGun = new RayGun(player.getPhysicalBody(), player.getOwnerType());
        assertNotNull(alienrayGun);
    }

    @Test
    public void testrayGunFire() {
        initApplication();
        init();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(2, 2));
        RayGun alienrayGun = new RayGun(player.getPhysicalBody(), player.getOwnerType());
        alienrayGun.attack(new Vector2(1,0));
        Bullet bullet = null;
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                bullet = (Bullet) entity;
                
            }
        }
        // null because not cooldown yet
        assertTrue(bullet == null);
        // reduce cooldown
        for (int i = 0; i < 100; i++) {
            alienrayGun.reduceCooldown();
        }

        alienrayGun.attack(new Vector2(1,0));

        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                bullet = (Bullet) entity;
            }
        }
        // not null after cooldown
        assertTrue(bullet != null);
    }


}
