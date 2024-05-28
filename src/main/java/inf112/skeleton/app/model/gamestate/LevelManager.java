package inf112.skeleton.app.model.gamestate;

import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.alien.Alien;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.mapfactories.Map;


public class LevelManager {
    private int currentLevel;
    private int mapWidth;
    private int mapHeight;
    private int difficulty;

    private static final int INITIAL_MAP_SIZE = 30;
    private static final int MAP_SIZE_INCREMENT = 5;
    private static final int INITIAL_DIFFICULTY = 1;

    public static boolean drawPath;

    /**
     * Constructor for LevelManager
     * Initializes the current level to 1, the map size to 30x30, and the difficulty to 1
     */

    public LevelManager() {
        this.currentLevel = 1;
        this.mapWidth = INITIAL_MAP_SIZE;
        this.mapHeight = INITIAL_MAP_SIZE;
        this.difficulty = INITIAL_DIFFICULTY;
        LevelManager.drawPath = false;
    }

    /**
     * Method to increase the level, map size, and difficulty
     */

    public void nextLevel() {
        currentLevel++;
        mapWidth += MAP_SIZE_INCREMENT;
        mapHeight += MAP_SIZE_INCREMENT;
        difficulty++; 
    }

    /**
     * Method to get the current level
     * @return currentLevel
     */

    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Method to get the map width
     * @return mapWidth
     */

    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Method to get the map height
     * @return mapHeight
     */

    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Method to get the difficulty
     * @return difficulty
     */

    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Method to check is ready to level up
     * @return true if level up, false otherwise
     */

    public static boolean isLevelUp(Entity playerPos, Map map, int spawnX, int spawnY) {
        if (isEntityMapEmpty()) {
            if (playerPos.getPosition().x > spawnX && playerPos.getPosition().y > spawnY && playerPos.getPosition().x < spawnX + 1 && playerPos.getPosition().y < spawnY + 1) {
                LevelManager.drawPath = false;
                return true;
            } 
            if (!LevelManager.drawPath) {
                map.drawPath(playerPos.getPosition());
                LevelManager.drawPath = true;
            }
        }
        return false;
    }

    /**
     * checks if EntityMap is empty
     */
    private static boolean isEntityMapEmpty() {
        for (Entity entity : Box2DWorldStatic.entityMap.values()) {
            if (entity instanceof Alien) {
                return false;
            }
        }
        return true;
    }


}