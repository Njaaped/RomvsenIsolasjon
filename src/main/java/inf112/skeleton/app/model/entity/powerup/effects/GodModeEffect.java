package inf112.skeleton.app.model.entity.powerup.effects;

import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.view.Media;

public class GodModeEffect implements PowerUpEffect {

    private float timer;
    private int textureIndex;
    private final int duration;
    private float oldHealth;
    public GodModeEffect(int duration) {
        this.duration = duration;
    }

    @Override
    public void activate(Player player) {
        player.setImunity(true);
        player.changeSpeed(2.5f);
        player.setPrimaryTexture(Media.getGodModeColors()[0]);
    }

    @Override
    public void deactivate(Player player) {
        player.setImunity(false);
        player.setPrimaryTexture(Media.player);
        player.resetSpeed();
    }

    @Override
    public void update(Player player, float deltaTime) {
        timer += deltaTime;
        textureIndex += 1;
        player.setPrimaryTexture(Media.getGodModeColors()[textureIndex % Media.getGodModeColors().length]);
        if (timer >= duration) {
            player.removePowerUp(this);
        }
    }
}
