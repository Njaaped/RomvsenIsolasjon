
package inf112.skeleton.app.model.mapfactory.maputils;

import org.junit.jupiter.api.Test;

import inf112.skeleton.app.model.mapfactories.maputils.AStar;
import inf112.skeleton.app.model.mapfactories.maputils.AStar.Node;
import inf112.skeleton.app.model.mapfactories.maputils.CreateMap;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MapCreationTests {

    @Test
    public void testMapCreation() {
        CreateMap map = new CreateMap(100, 100, 123, 5);
        int[][] intmap = map.getIntmap();
        assertNotNull(intmap);
        assertEquals(100, intmap.length);
        assertEquals(100, intmap[0].length);
    }

    @Test
    public void testCountIslands() {
        CreateMap map = new CreateMap(100, 100, 123, 5);
        int islands = map.countIslands();
        assertTrue(islands >= 1);
    }


    @Test
    public void testAllNotZero() {
        CreateMap map = new CreateMap(100, 100, 123, 5);
        map.countIslands();
        int[][] intmap = map.getIntmap();
        boolean allNotZero = false;
        for (int i = 0; i < intmap.length; i++) {
            for (int j = 0; j < intmap[i].length; j++) {
                if (intmap[i][j] != 0) {
                    allNotZero = true;
                    break;
                }
            }
        }
        assertTrue(allNotZero);
    }

    @Test
    public void testHasWalls() {
        CreateMap map = new CreateMap(100,100, 123, 5);
        int[][] intmap = map.getIntmap();
        boolean hasWall = false;
        for (int i = 0; i < intmap.length; i++) {
            for (int j = 0; j < intmap[i].length; j++) {
                if (intmap[i][j] == 2) {
                    hasWall = true;
                    break;
                }
            }
        }
        assertTrue(hasWall);
    }

    @Test
    public void testNoEscapesToSpace() {
        CreateMap map = new CreateMap(100, 100, 123, 5);
        int[][] intmap = map.getIntmap();
        boolean hasSpace = false;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; i < intmap.length; i++) {
            for (int j = 0; j < intmap[i].length; j++) {
                if (intmap[i][j] == 0) {
                    for (int[] dir : dirs) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        if (x < 0 || x >= intmap.length || y < 0 || y >= intmap[i].length) {
                            continue;
                        } else if (intmap[x][y] == 1) {
                            hasSpace = true;
                            break;
                        }
                    }
                }
            }
        }
        assertFalse(hasSpace);
    }

    @Test
    public void testAlgorithm() {
        int[][] intmap = {
            {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}
        };
        List<Node> islands = AStar.algorithm(intmap, 0, 0, 2, 4);
        assert(islands.size() > 1);
    }

    @Test
    public void testNodeHash() {
        int[][] intmap = {
            {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}
        };
        List<Node> islands = AStar.algorithm(intmap, 0, 0, 2, 4);
        for (Node node : islands) {
            assertEquals(node.hashCode(), node.hashCode());
            assertEquals(node.getCol()*31 + node.getRow(), node.hashCode());
        }
    }

    @Test
    public void testGetters() {
        int[][] intmap = {
            {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}
        };
        List<Node> islands = AStar.algorithm(intmap, 0, 0, 1, 0);
        for (Node node : islands) {
            assertEquals(node.getCol(), 0);
            assertEquals(node.getRow(), 0);
            break;
        }
    }


    private int dfsSearch(int[][] intmap, int height, int width) {
        boolean[][] visited = new boolean[height][width];
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (intmap[i][j] == 1 && !visited[i][j]) {
                    dfs(intmap, visited, i, j);
                    count++;
                }
            }
        }
        return count;
    }   

    private void dfs(int[][] intmap, boolean[][] visited, int i, int j) {
        if (i < 0 || i >= intmap.length || j < 0 || j >= intmap[i].length || intmap[i][j] != 1 || visited[i][j]) {
            return;
        }
        visited[i][j] = true;
        dfs(intmap, visited, i + 1, j);
        dfs(intmap, visited, i - 1, j);
        dfs(intmap, visited, i, j + 1);
        dfs(intmap, visited, i, j - 1);
    }

    // @Test
    // public void testHoleAddedBigMap() {
    //     CreateMap map = new CreateMap(100, 100, 123, 5);
    //     int[][] intmap = map.getIntmap();
    //     boolean hasHole = false;
    //     for (int i = 0; i < intmap.length; i++) {
    //         for (int j = 0; j < intmap[i].length; j++) {
    //             if (intmap[i][j] == 4) {
    //                 hasHole = true;
    //                 break;
    //             }
    //         }
    //     }
    //     assertTrue(hasHole);
    // }
}
