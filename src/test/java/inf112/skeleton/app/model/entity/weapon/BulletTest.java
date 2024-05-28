package inf112.skeleton.app.model.entity.weapon;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.GL20;

import inf112.skeleton.app.model.entity.alien.AlienOne;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.Tile;
import inf112.skeleton.app.model.mapfactories.TileType;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.view.Media;

public class BulletTest {

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
    public void testBullet() {
        init();
        Bullet bullet = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,Player.class);
        assertTrue(bullet != null);
    }

    @Test
    public void testBulletCollisionWall() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Bullet bullet = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,Player.class);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                bullet = (Bullet) entity;
            }
        }
        assertTrue(bullet != null);
        bullet.interact(new Tile(TileType.WALL), true);
        Bullet newbullet = null;
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                newbullet = (Bullet) entity;
            }
        }
        assertTrue("bullet should not exists, " + newbullet , newbullet == null);
    }

    @Test
    public void testBulletCollisionPlayer() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Bullet bullet = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,AlienOne.class);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                bullet = (Bullet) entity;
            }
        }
        assertTrue(bullet != null);
        bullet.interact(new Player(new Vector2(0, 0)), true);
        Bullet newbullet = null;
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                newbullet = (Bullet) entity;
            }
        }
        assertTrue("bullet should not exists, " + newbullet , newbullet == null);
    }

    @Test
    public void testBulletCollisionAlien() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Player player = new Player(new Vector2(0, 0));
        Bullet bullet = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,Player.class);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                bullet = (Bullet) entity;
            }
        }
        assertTrue(bullet != null);
        bullet.interact(new AlienOne(new Vector2(0, 0)), true);
        Bullet newbullet = null;
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                newbullet = (Bullet) entity;
            }
        }
        assertTrue("bullet should not exists, " + newbullet , newbullet == null);
    }

    @Test
    public void testBulletCollisionBullet() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Bullet bullet = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,Player.class);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                bullet = (Bullet) entity;
            }
        }
        assertTrue(bullet != null);
        Bullet bullet2 = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,AlienOne.class);
        bullet.interact(bullet2, true);
        bullet2.interact(bullet, true);
        Bullet newbullet = null;
        Box2DWorldStatic.stepWorld(0.1f);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                newbullet = (Bullet) entity;
            }
        }
        assertTrue("bullet should not exists, " + newbullet , newbullet == null);
    }

    @Test
    public void testUpdate() {
        init();
        initApplication();
        Box2DWorldStatic.startBox2DWorld();
        Bullet bullet = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,Player.class);
        for (Entity entity : Box2DWorldStatic.getEntities()) {
            if (entity instanceof Bullet) {
                bullet = (Bullet) entity;
            }
        }
        assertTrue(bullet != null);
        Vector2 pos1 = bullet.getPosition().cpy();
        bullet.update(0.1f);
        Vector2 pos2 = bullet.getPosition();
        assertTrue("Bullet did not move", pos1 != pos2);
    }

    @Test
    public void testGetters() {
        init();
        initApplication();
        Gdx.gl = Mockito.mock(GL20.class);
        Media.load_assets();
        Bullet bullet = new Bullet(new Vector2(0, 0), new Vector2(1, 0), 0f,0,0f,Player.class);
        assertTrue(bullet.getDamage() == 0);
        assertTrue(bullet.getPrimaryTexture() != null);
        assertTrue(Bullet.getDirection(bullet) == 0);
        assertTrue(bullet.getPosition() != null);
        assertTrue(bullet.getOwnerType() == Player.class);
    }

    
}
