package inf112.skeleton.app.model.mapfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.app.model.mapfactories.maputils.IntegerToTile;
import inf112.skeleton.app.model.mapfactories.maputils.CreateMap;
import inf112.skeleton.app.view.Media;

import java.util.HashMap;
import java.util.Random;

public class InsaneMapFactory implements MapFactory {

    private int seed;

    public InsaneMapFactory(int seed) {
        this.seed = seed;
    }

    public InsaneMapFactory() {
        this(new Random().nextInt());
    }

    private static Tile getWallTile(int row, int col, int[][] intmap) {
        HashMap<String, Texture> textureMap = new HashMap<>();
        populateTextureMap(textureMap);

        Tile tile = new Tile(TileType.WALL);
        int[][] neighbours = getNeighbours(row, col, intmap);
        String orientation = tile.getWallType(neighbours);

        // Determine the texture based on the orientation string
        for (HashMap.Entry<String, Texture> entry : textureMap.entrySet()) {
            if (entry.getKey().equals(orientation)) {
                tile.setPrimaryTexture(entry.getValue());
                break;
            }
        }

        return tile;
    }

    private static void populateTextureMap(HashMap<String, Texture> textureMap) {
        textureMap.put("S", Media.wallBottom);
        textureMap.put("N1", Media.wallTop);
        textureMap.put("N2", Media.wallTop2);
        textureMap.put("N3", Media.wallTop3);
        textureMap.put("W", Media.wallRight);
        textureMap.put("E", Media.wallLeft);
        textureMap.put("EW", Media.wallVertical);
        textureMap.put("NEI", Media.wallTopLeftInside);
        textureMap.put("NWI", Media.wallTopRightInside);
        textureMap.put("SEI", Media.wallBottomLeftInside);
        textureMap.put("SWI", Media.wallBottomRightInside);
        textureMap.put("NEO", Media.wallTopLeftOutside);
        textureMap.put("NWO", Media.wallTopRightOutside);
        textureMap.put("SEO", Media.wallBottomLeftOutside);
        textureMap.put("SWO", Media.wallBottomRightOutside);
    }

    private static int[][] getNeighbours(int row, int col, int[][] intmap) {
        int[][] neighbours = new int[3][3];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    neighbours[i + 1][j + 1] = intmap[row + i][col + j];
                } catch (ArrayIndexOutOfBoundsException e) {
                    neighbours[i + 1][j + 1] = 0;
                }
            }
        }
        return neighbours;
    }

    @Override
    public Map generate(int width, int height, int difficulty) {
        CreateMap createMap = new CreateMap(width, height, seed, difficulty);
        int[][] intmap = createMap.getIntmap();
        Array<Array<Tile>> map = new Array<>(true, height);
        populateMap(width, height, intmap, map);
        return new Map(map);
    }

    @Override
    public void setSeed(long seed) {
        this.seed = (int) seed;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    /**
     * Populates the map with tiles based on the intmap
     * 0 = empty
     * 1 = floor
     * 2 = wall
     * 4 = hole
     * 8 = enemy spawn
     * 9 = player spawn
     * @param width map width
     * @param height map height
     * @param intmap integer map
     * @param map map to be populated
     */

    private void populateMap(int width, int height, int[][] intmap, Array<Array<Tile>> map) {
        for (int row = 0; row < height; row++) {
            map.add(new Array<>(true, width));
            for (int col = 0; col < width; col++) {
                int tileInt = intmap[row][col];
                if (tileInt != 2) {
                    Tile tile = new Tile(IntegerToTile.intToTile(tileInt));
                    tile.setPrimaryTexture(IntegerToTile.intToTexture(tileInt));
                    map.get(row).add(tile);
                } else {
                    Tile tile = getWallTile(row, col, intmap);
                    map.get(row).add(tile);
                }
                
            }
        }
    }

    
}
