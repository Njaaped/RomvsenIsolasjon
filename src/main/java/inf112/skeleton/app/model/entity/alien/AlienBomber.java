package inf112.skeleton.app.model.entity.alien;


import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;
import inf112.skeleton.app.model.audio.SoundEffectEvent;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.weapon.Cooldown;
import inf112.skeleton.app.view.Media;

public class AlienBomber extends Alien implements Cooldown {
    private static final double DEFAULT_SPEED = 2;
    private static final float WIDTH = 1f;
    private static final float HEIGHT = 1f;
    private static final int POINT_VALUE = 15;
    private boolean exploded;
    private int surviveTime;
    private final int TIME_TO_SURVIVE_AFTER_EXPLOSION = 20;
    private int textureCounter;
    private final int bombDamage;

    
  
    public AlienBomber(Vector2 pos, int bombDamage) {
        super(
            pos,
            WIDTH,
            HEIGHT,
            DEFAULT_SPEED,
                POINT_VALUE,
                Media.alienBomber1
        );
        this.textureCounter = 0;
        this.exploded = false;
        this.surviveTime = TIME_TO_SURVIVE_AFTER_EXPLOSION;
        this.bombDamage = bombDamage;
    }
    
    @Override
    protected void updateBehavior(float deltaTime) {
      if (!exploded) {
        this.primaryTexture = Media.getAlienBomber()[textureCounter % Media.getAlienBomber().length];
        textureCounter++;
      }
      reduceCooldown();
    }
  
    @Override
    public void interactBehavior(Interactable target, boolean isStart) {
      if (target instanceof Player) {
        if (isStart) {
          Player player = (Player) target;
          if (!exploded) {
              player.changeHealth(-bombDamage);
              this.exploded = true;
              this.primaryTexture = Media.explosion;
              IEventBus bus = GlobalEventBus.getInstance(2);
              bus.trigger("SoundEffect", new SoundEffectEvent(this, "explode"));
          }
        }
      }
    }

    @Override
    public void reduceCooldown() {
        if (exploded) {
            surviveTime--;
        }
        if (surviveTime <= 0) {
            dispose();
        }
    }

    
}
