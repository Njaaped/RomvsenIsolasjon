package inf112.skeleton.app.model.mapfactories.maputils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class CreateMap {


    private final int[][] intmap;
    private final int difficulty;
    private final int WIDTH;
    private final int HEIGHT;
    private final Random rand;

    private final int smallestRoomSize = 2;

    private boolean first;

    public CreateMap(int width, int height, int seed, int difficulty) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.rand = new Random(seed);
        this.intmap = new int[HEIGHT][WIDTH];
        this.first = true;
        this.difficulty = difficulty;
        initializeMap();
    }

    /**
     * Initializes the map with a random number of rooms
     */

    private void initializeMap() {
        int threshold = 100;
        int count = 0;
        boolean added;
        while (count < threshold) {
            int largestRoomSize = 4;
            added = addTiles(smallestRoomSize, largestRoomSize);
            if (added) {
                count = 0;
            } else {
                count++;
            }
        }
        int maxAttempts = 1000;
        int islands = countIslands();
        int attempt = 0;
        while (islands > 1 && attempt < maxAttempts) {
            islands = countIslands();
            attempt++;
        }
        fillWalls();
        int[] spawnpos = createSpawn();
        if (difficulty != 0) {
            attempt = 0;
            while(!(hasAliens() && attempt < maxAttempts)) {
                addAliens(spawnpos, 0.03f * difficulty);
                attempt++;
            }
        }

        addStars(spawnpos, 0.015f);
    }

    /**
     * Checks if the map has aliens
     * @return true if the map has aliens, false otherwise
     */
    private boolean hasAliens() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (intmap[i][j] == 8) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a random number of tiles to the map
     * @param least the smallest room size
     * @param most the largest room size
     * @return true if a room was added, false otherwise
     */
    private boolean addTiles(int least, int most) {
        int row = rand.nextInt(HEIGHT);
        int col = rand.nextInt(WIDTH);
        int roomSize = rand.nextInt(most - least) + least;
        if (intmap[row][col] == 0 && checkSurroundingTiles(row, col, roomSize)) {
            fillSurroundingTiles(row, col, roomSize);
            return true;
        }
        return false;
    }

    /**
     * Checks if the surrounding tiles are empty, with +2 margin
     * @param row current row
     * @param col current col
     * @param roomSize size of room
     * @return True if surrounding tiles are empty, false if not
     */
    private boolean checkSurroundingTiles(int row, int col, int roomSize) {
        for (int i = row - roomSize - 1; i < row + roomSize + 1; i++) {
            for (int j = col - roomSize - 1; j < col + roomSize + 1; j++) {
                if (i >= HEIGHT || j >= WIDTH || i < 0 || j < 0 || intmap[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Fills the surrounding tiles with 1
     * @param row current row
     * @param col current column
     * @param roomSize size of room
     */
    private void fillSurroundingTiles(int row, int col, int roomSize) {
        for (int i = row - roomSize; i < row + roomSize; i++) {
            for (int j = col - roomSize; j < col + roomSize; j++) {
                intmap[i][j] = 1;
            }
        }
    }

    /**
     * Counts the number of islands in the map and connects the Islands
     * @return the number of islands
     */
    public int countIslands() {
        boolean[][] visited = new boolean[HEIGHT][WIDTH];
        int count = 0;
        int dist = 0;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (intmap[row][col] == 1 && !visited[row][col]) {
                    if (first) {
                        dfsSelectPointsInDifferentRooms(row, col, visited, dist);
                    } else {
                        dfs(row, col, visited);
                    }
                    count++;
                    connectToIsland(row, col, visited);
                }
            }
        }
        first = false;
        return count;
    }


    /**
     * Connects the island to the closest island
     * @param row current row
     * @param col current column
     * @param visited visited
     */

    private void connectToIsland(int row, int col, boolean[][] visited) {
        int[] closestIsland = findClosestIsland(row, col, visited);
        if (closestIsland != null) {
            createShortestPath(row, col, closestIsland[0], closestIsland[1]);
        }
    }

    /**
     * Finds the closest island to the given coordinates
     * @param row current row
     * @param col current column
     * @param visited visited
     * @return the coordinates of the closest island
     */
    private int[] findClosestIsland(int row, int col, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visitedCopy = new boolean[HEIGHT][WIDTH];
        queue.offer(new int[]{row, col});
        int[] directions = {0, 1, 0, -1, 1, 0, -1, 0};
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int i = 0; i < 8; i += 2) {
                int newRow = current[0] + directions[i];
                int newCol = current[1] + directions[i + 1];
                if (newRow >= 0 && newRow < HEIGHT && newCol >= 0 && newCol < WIDTH && intmap[newRow][newCol] == 1 && !visited[newRow][newCol]) {
                    return new int[]{newRow, newCol};
                } else if (newRow >= 0 && newRow < HEIGHT && newCol >= 0 && newCol < WIDTH && intmap[newRow][newCol] != 2  && !visitedCopy[newRow][newCol]) {
                    queue.offer(new int[]{newRow, newCol});
                    visitedCopy[newRow][newCol] = true;
                }
            }
        }
        return null;
    }

    /**
     * Creates the shortest path between two coordinates
     * @param startRow starting row
     * @param startCol start column
     * @param endRow end row
     * @param endCol end column
     */
    private void createShortestPath(int startRow, int startCol, int endRow, int endCol) {
        List<AStar.Node> path = AStar.findPath(intmap, startRow, startCol, endRow, endCol, false);
        assert path != null;
        for (AStar.Node node : path) {
            intmap[node.getRow()][node.getCol()] = 1;
        }
        
    }

    /**
     * Depth first search to find islands
     * @param row this row
     * @param col this column
     * @param visited visited
     */
    private void dfs(int row, int col, boolean[][] visited) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH || intmap[row][col] != 1 || visited[row][col]) {
            return;
        }
        visited[row][col] = true;
        dfs(row + 1, col, visited);
        dfs(row - 1, col, visited);
        dfs(row, col + 1, visited);
        dfs(row, col - 1, visited);
    }

    /**
     * Depth first search to find points in different rooms
     * @param row this row
     * @param col this column
     * @param visited visited
     * @param dist distance traveled
     * @return distance
     */
    private int dfsSelectPointsInDifferentRooms(int row, int col, boolean[][] visited, int dist) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH || intmap[row][col] == 0 || visited[row][col]) {
            return 0;
        }

        visited[row][col] = true;
        int totalDist = 1;
        totalDist += dfsSelectPointsInDifferentRooms(row + 1, col, visited, dist + 1);
        totalDist += dfsSelectPointsInDifferentRooms(row - 1, col, visited, dist + 1);
        totalDist += dfsSelectPointsInDifferentRooms(row, col + 1, visited, dist + 1);
        totalDist += dfsSelectPointsInDifferentRooms(row, col - 1, visited, dist + 1);
        return totalDist;
    }


    /**
     * Fills the walls of the map by setting surrounding tiles of floor tiles to walls, including corners.
     */
    private void fillWalls() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                // Check if current tile is floor
                if (intmap[row][col] == 0) {
                    // Check all eight surrounding tiles: up, down, left, right, and four corners
                    if ((row > 0 && intmap[row - 1][col] == 1) ||
                            (row < HEIGHT - 1 && intmap[row + 1][col] == 1) ||
                            (col > 0 && intmap[row][col - 1] == 1) ||
                            (col < WIDTH - 1 && intmap[row][col + 1] == 1) ||
                            (row > 0 && col > 0 && intmap[row - 1][col - 1] == 1) ||
                            (row > 0 && col < WIDTH - 1 && intmap[row - 1][col + 1] == 1) ||
                            (row < HEIGHT - 1 && col > 0 && intmap[row + 1][col - 1] == 1) ||
                            (row < HEIGHT - 1 && col < WIDTH - 1 && intmap[row + 1][col + 1] == 1)) {
                        // Set the current tile to wall
                        intmap[row][col] = 2;
                    }
                }
            }
        }
    }



    /**
     * Creates a spawn point
     */
    private int[] createSpawn() {
        int row = rand.nextInt(HEIGHT);
        int col = rand.nextInt(WIDTH);
        while (intmap[row][col] != 1) {
            row = rand.nextInt(HEIGHT);
            col = rand.nextInt(WIDTH);
        }
        intmap[row][col] = 9;
        return new int[]{row, col};
    }

    /**
     * Adds aliens to the map
     */
     private void addAliens(int[] spawnpos, float spawnChance) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[][] visited = new int[HEIGHT][WIDTH];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(spawnpos);
        visited[spawnpos[0]][spawnpos[1]] = 1;
        for (int row = spawnpos[0] - 10; row < spawnpos[0] + 10; row++) {
            for (int col = spawnpos[1] - 10; col < spawnpos[1] + 10; col++) {
                if (row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH && intmap[row][col] == 1) {
                    visited[row][col] = 1;
                    queue.offer(new int[]{row, col});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newRow = current[0] + directions[i][0];
                int newCol = current[1] + directions[i][1];
                if (newRow >= 0 && newRow < HEIGHT && newCol >= 0 && newCol < WIDTH && intmap[newRow][newCol] == 1 && visited[newRow][newCol] == 0) {
                    if (rand.nextFloat() < spawnChance) {
                        intmap[newRow][newCol] = 8;
                    }
                    visited[newRow][newCol] = 1;
                    queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
        }


    /**
    * adds stars to the map

    */
    private void addStars(int[] spawnpos, float spawnChance) {
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int[][] visited = new int[HEIGHT][WIDTH];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(spawnpos);
    visited[spawnpos[0]][spawnpos[1]] = 1;

    while (!queue.isEmpty()) {
        int[] current = queue.poll();
        for (int i = 0; i < 4; i++) {
            int newRow = current[0] + directions[i][0];
            int newCol = current[1] + directions[i][1];
            if (newRow >= 0 && newRow < HEIGHT && newCol >= 0 && newCol < WIDTH && intmap[newRow][newCol] == 1 && visited[newRow][newCol] == 0) {
                if (rand.nextFloat() < spawnChance) {
                    intmap[newRow][newCol] = 7;
                }
                visited[newRow][newCol] = 1;
                queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }


    /**
     * Returns the integer map
     * @return integer map
     */
    public int[][] getIntmap() {
        return intmap;
    }
}
