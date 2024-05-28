package inf112.skeleton.app.model.gamestate;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.entity.player.Player;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LevelManagerTest {
  LevelManager levelManager;
  /**
   * Setup method called before each of the test methods
   */
  @BeforeEach
  void setUpBeforeEach() {
    levelManager = new LevelManager();
  }

  @Test
  void testGetters() {
    assertEquals(levelManager.getCurrentLevel(), 1);
    assertEquals(levelManager.getDifficulty(), 1);
    assertEquals(levelManager.getMapHeight(), 30);
    assertEquals(levelManager.getMapWidth(), 30);
  }

  @Test
  void testNextLevel() {
    assertEquals(levelManager.getCurrentLevel(), 1);
    assertEquals(levelManager.getDifficulty(), 1);

    levelManager.nextLevel();

    assertEquals(levelManager.getCurrentLevel(), 2);
    assertEquals(levelManager.getDifficulty(), 2);
  }

  @Test
  void testIsLevelUpFalse() {
    Box2DWorldStatic.startBox2DWorld();
    Player p = new Player(new Vector2(2, 2));
    InsaneMapFactory mf = new InsaneMapFactory(0);
    Map m = mf.generate(100, 100, 1);
    System.out.println(Box2DWorldStatic.entityMap);


    assertFalse(LevelManager.isLevelUp(p, m, m.getSpawnPoint()[0], m.getSpawnPoint()[1]));
    assertTrue(LevelManager.drawPath);
  }
  @Test
  void testIsLevelUpTrue() {
    Box2DWorldStatic.startBox2DWorld();
    InsaneMapFactory mf = new InsaneMapFactory(0);
    Map m = mf.generate(100, 100, 0);
    Player p = new Player(new Vector2(m.getSpawnPoint()[1], m.getSpawnPoint()[0]));

    System.out.println(Box2DWorldStatic.entityMap);


    assertTrue(LevelManager.isLevelUp(p, m, m.getSpawnPoint()[1], m.getSpawnPoint()[0]));
  }
}
