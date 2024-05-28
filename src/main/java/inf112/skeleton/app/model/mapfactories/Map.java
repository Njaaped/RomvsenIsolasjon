package inf112.skeleton.app.model.mapfactories;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.model.mapfactories.maputils.AStar;
import inf112.skeleton.app.view.Media;

// import list
import java.util.List;

public class Map implements Iterable<MapEntry> {
    // The map of the game
    private final Array<Array<Tile>> tiles;
    private final int TILE_SIZE = 1;
    private final int[][] intMap;

    public Map(Array<Array<Tile>> tiles) {
        this.tiles = tiles;
        intMap = getGrid(tiles);
    }

    /**
     * Get the width of the map.
     * @return width
     */

    public int getWidth() {
        return tiles.get(0).size;
    }

    /**
     * Get the height of the map.
     * @return height
     */

    public int getHeight() {
        return tiles.size;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    /**
     * Get a tile on the map.
     * !IMPORTANT Tile is currently mutable, be careful with modifying it.
     * @param row The row of the tile to get
     * @param col The col of the tile to get
     * @return The tile in the grid.
     */
    public Tile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }

    /**
     * Get the tiles of the map. as a 2D array.
     * @return Array of array of tiles
     */

    public Array<Array<Tile>> getTiles() {
        return tiles;
    }

    public Vector2 convertToWorldPosition(int row, int col) {
        return new Vector2(col * TILE_SIZE, row * TILE_SIZE);
    }

    @Override
    public Iterator<MapEntry> iterator() {
        Array<MapEntry> entries = new Array<>();
        for (int row = 0; row < tiles.size; row++) {
            for (int col = 0; col < tiles.get(row).size; col++) {
                entries.add(new MapEntry(row, col, getTile(row, col).getTiletype()));
            }
        }

        return entries.iterator();
    }

    @Override
    public String toString() {
        StringBuilder printMap = new StringBuilder();
        for (int row = 0; row < tiles.size; row++) {
            for (int col = 0; col < tiles.get(0).size; col++) {
                printMap.append(getTile(row, col).getTiletype()).append(" ");
            }

            printMap.append("\n");
        }

        return printMap.toString();
    }

    /**
     * Get the position of the player spawn point.
     */

    public int[] getSpawnPoint() {
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (getTile(row, col).getTiletype() == TileType.PLAYER_SPAWN) {
                    return new int[]{row, col};
                }
            }
        }
        return new int[]{0, 0};
    }

    /**
     * Get the Integer version of the map.
     */

    public int[][] getIntMap() {
        return intMap;
    }

    /**
     * Draws path from player to spawn point
     */

    public void drawPath(Vector2 playerPos) {
        int[] spawn = getSpawnPoint();
        int[][] grid = getGrid(tiles);
        List<AStar.Node> path = AStar.algorithm(grid, spawn[0], spawn[1],(int) playerPos.y, (int) playerPos.x);
        for (AStar.Node node : path) {
            tiles.get(node.getRow()).get(node.getCol()).setPrimaryTexture(Media.brightGreen);
            tiles.get(node.getRow()).get(node.getCol()).setSecondaryTexture(Media.brightGreen);
        }
    }

     /**
     * Helper method to generate the intmap from the tileGrid
     * @param tileGrid grid of tiles
     * @return integer map
     */

     private int[][] getGrid(Array<Array<Tile>> tileGrid) {
        int[][] grid = new int[tileGrid.size][tileGrid.get(0).size];
        for (int row = 0; row < tileGrid.size; row++) {
            for (int col = 0; col < tileGrid.get(0).size; col++) {
                Tile tile = tileGrid.get(row).get(col);
                if (tile.getTiletype() == TileType.WALL) {
                    grid[row][col] = 2;
                } else if (tile.getTiletype() == TileType.FLOOR) {
                    grid[row][col] = 1;
                } else if (tile.getTiletype() == TileType.POWERUP_SPAWN) {
                    grid[row][col] = 1;
                } else if (tile.getTiletype() == TileType.PLAYER_SPAWN) {
                    grid[row][col] = 1;
                } else if (tile.getTiletype() == TileType.ENEMY_SPAWN) {
                    grid[row][col] = 1;
                } else {
                    grid[row][col] = 0;
                }
            }
        }
        return grid;
    }
}
