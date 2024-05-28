package inf112.skeleton.app.controller;


public interface ControllableModel {
    /**
     * Tells the player to move in a direction. To stop moving, set to 0, 0.
     * @param x Should be between -1 and 1. Positive meaning right movement
     * @param y Should be between -1 and 1. Positive meaning up movement
     */
    void moveDirection(int x, int y);

    /**
     * set the sprinting state of the player
     * @param sprinting sprint state
     */
    void isSprinting(boolean sprinting);

    /**
     * The main game loop. Tells the model to update. Will be called many times per second.
     * @param deltaTime The time since the last update in seconds.
     */
    void update(float deltaTime);

    /**
     * Toggle debug mode
     */
    void toggleDebug();
    
    /**
     * Tells the player whether it should be attacking or not.
     * @param b Attack state
     */
    void setAttacking(boolean b);
    
    /**
    * pause the game
    */
    void pauseGame();

    /**
     * temporary game over method
     */

    void gameOver();

    /**
     * restart the game from controller
     */
    void restartGame();

    /**
     * Stops the game from the controller
     */
    void stopGame();
  void attackDirection(int x, int y);
}
