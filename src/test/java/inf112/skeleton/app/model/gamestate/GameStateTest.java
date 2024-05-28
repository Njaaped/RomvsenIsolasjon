package inf112.skeleton.app.model.gamestate;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class GameStateTest {
  @Test
  void testEnums() {
    assertEquals(GameState.valueOf("MAIN_MENU"), GameState.MAIN_MENU);
    assertEquals(GameState.valueOf("RUNNING"), GameState.RUNNING);
    assertEquals(GameState.valueOf("PAUSED"), GameState.PAUSED);
    assertEquals(GameState.valueOf("GAME_OVER"), GameState.GAME_OVER);
  }
}
