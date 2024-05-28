package inf112.skeleton.app.model.entity.powerup;

import inf112.skeleton.app.model.entity.powerup.effects.PowerUpEffect;

public interface PowerUpAble {

    /**
     * Removes a powerup from the player
     * @param powerUp the powerup to remove
     */
    
    public void removePowerUp(PowerUpEffect powerUp);

    /**
     * Adds a powerup to the player
     * @param powerUp the powerup to add
     */

    public void addPowerUp(PowerUpEffect powerUp);

}
