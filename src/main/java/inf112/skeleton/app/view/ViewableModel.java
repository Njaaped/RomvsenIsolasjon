package inf112.skeleton.app.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.Tile;
import com.badlogic.gdx.math.Vector2;

/**
 * Interface for the view components in the game to interact with the game model.
 * Provides methods to retrieve game state information such as entities, tiles, map,
 * and player-specific data like position and health.
 */
public interface ViewableModel {

  /**
   * Retrieves all entities present in the current game model.
   * 
   * @return an Array of all Entity instances currently active in the game
   */
  public Array<Entity> getEntities();

  /**
   * Retrieves the grid of tiles representing the game map.
   * 
   * @return a 2D Array where each Array<Tile> represents a row of tiles
   */
  public Array<Array<Tile>> getTiles();

  /**
   * Retrieves the current map of the game.
   * 
   * @return the current Map instance
   */
  public Map getMap();

  /**
   * Gets the position of the player in the world coordinates.
   * 
   * @return a Vector2 representing the player's current position in world coordinates
   */
  public Vector2 getPlayerPosition();

  /**
   * Retrieves the current health of the player as a percentage.
   * 
   * @return a float value representing the percentage of the player's health (0.0 to 100.0)
   */
  public float getPlayerHealthPercentage();

  /**
   * Instructs the view to draw the hitboxes for all entities on the given camera.
   * This is typically used for debugging purposes to visually confirm entity boundaries.
   * 
   * @param camera the OrthographicCamera on which to draw the hitboxes
   */
  void drawHitboxes(OrthographicCamera camera);

  /**
   * Retrieves the current score of the player.
   * 
   * @return an integer representing the player's current score
   */
  int getScore();
}
