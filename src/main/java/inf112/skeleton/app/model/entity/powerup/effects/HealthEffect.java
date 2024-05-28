package inf112.skeleton.app.model.entity.powerup.effects;

import inf112.skeleton.app.model.entity.player.Player;

public class HealthEffect implements PowerUpEffect {
    private final int healthAmount;

    public HealthEffect(int healthAmount) {
        this.healthAmount = healthAmount;
    }

    @Override
    public void activate(Player player) {
        player.changeHealth(healthAmount);
        player.removePowerUp(this);
    }

    @Override
    public void deactivate(Player player) {
        
    }

    @Override
    public void update(Player player, float deltaTime) {
        
    }

    
}
