package inf112.skeleton.app.model.entity.powerup.effects;

import inf112.skeleton.app.model.entity.player.Player;

public interface PowerUpEffect {

    /**
     * Activates the powerup effect
     * @param player the player that the effect is applied to
     */
    public void activate(Player player);

    /**
     * Deactivates the powerup effect
     * @param player the player that the effect is applied to
     */

    public void deactivate(Player player);

    /**
     * Updates the powerup effect
     * @param player the player that the effect is applied to
     * @param deltaTime the time since the last update
     */

    public void update(Player player, float deltaTime);
}
