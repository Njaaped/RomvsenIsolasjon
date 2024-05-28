package inf112.skeleton.app.model.entity.powerup.effects;

import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.weapon.weapons.Gun;
import inf112.skeleton.app.model.entity.weapon.weapons.RayGun;

public class RayGunEffect implements PowerUpEffect {

    private float timer;
    private final int duration;

    public RayGunEffect(int duration) {
        this.duration = duration;
    }

    @Override
    public void activate(Player player) {
        player.setWeapon(new RayGun(player.getPhysicalBody(), player.getClass()));
    }

    @Override
    public void deactivate(Player player) {
        player.setWeapon(new Gun(player.getPhysicalBody(), player.getClass()));
    }

    @Override
    public void update(Player player, float deltaTime) {
        timer += deltaTime;
        if (timer >= duration) {
            player.removePowerUp(this);
        }
    }
    
    
}
