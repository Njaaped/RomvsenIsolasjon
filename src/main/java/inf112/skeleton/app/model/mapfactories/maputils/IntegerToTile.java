
package inf112.skeleton.app.model.mapfactories.maputils;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.model.mapfactories.TileType;


/*
 * Class to convert integers to tiles
 * 0 = empty
 * 1 = floor
 * 2 = wall
 * 4 = hole
 * 8 = enemy spawn
 * 9 = player spawn
 */

public class IntegerToTile {

    public static final int empty = 0;
    public static final int floor = 1;
    public static final int wall = 2;
    public static final int hole = 4;
    public static final int star_spawn = 7;
    public static final int enemy_spawn = 8;
    public static final int player_spawn = 9;

    public static TileType intToTile(int i) {
      return switch (i) {
        case IntegerToTile.floor -> TileType.FLOOR;
        case IntegerToTile.wall -> TileType.WALL;
        case IntegerToTile.hole -> TileType.HOLE;
        case IntegerToTile.star_spawn -> TileType.POWERUP_SPAWN;
        case IntegerToTile.enemy_spawn -> TileType.ENEMY_SPAWN;
        case IntegerToTile.player_spawn -> TileType.PLAYER_SPAWN;
        default -> TileType.EMPTY;
      };
    }

    public static Texture intToTexture(int i) {
      return switch (i) {
        case IntegerToTile.floor -> Media.ground;
        case IntegerToTile.wall -> Media.wallTop;
        case IntegerToTile.hole -> Media.space1;
        case IntegerToTile.star_spawn -> Media.ground;
        case IntegerToTile.enemy_spawn -> Media.ground;
        case IntegerToTile.player_spawn -> Media.ground;
        default -> Media.space5;
      };
    }

    public static int intToSize (int i) {
      return switch (i) {
        case IntegerToTile.empty -> 1;
        case IntegerToTile.floor -> 1;
        case IntegerToTile.wall -> 1;
        case IntegerToTile.hole -> 1;
        case IntegerToTile.enemy_spawn -> 1;
        case IntegerToTile.player_spawn -> 1;
        default -> 1;
      };
    }




}
