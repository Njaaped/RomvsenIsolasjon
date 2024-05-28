package inf112.skeleton.app.model.entity.alien;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.weapon.Cooldown;
import inf112.skeleton.app.model.entity.weapon.Weapon;
import inf112.skeleton.app.model.entity.weapon.weapons.AlienGun;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.view.Media;

public class AlienTwo extends Alien {
  private static final double DEFAULT_SPEED = 1;
  private static final float WIDTH = 1f;
  private static final float HEIGHT = 1f;
  private static final int POINT_VALUE = 20;
  private Weapon weapon;

  public AlienTwo(Vector2 pos) {
    super(
      pos,
      WIDTH,
      HEIGHT,
      DEFAULT_SPEED,
      POINT_VALUE,
      Media.alienTwo
    );
    this.weapon = new AlienGun(this.physicalBody, this.getClass());
  }

  @Override
  public void updateBehavior(float deltaTime) {
    if (weapon instanceof Cooldown cooldownWeapon) {
      cooldownWeapon.reduceCooldown();
    }
    Player player = (Player) Box2DWorldStatic.getPlayer();
    double dist = distanceTo(player);
    boolean isInSight = false;
    if (dist < 15d) {
      isInSight = canSee(player);
    }
    if (isInSight) {
      this.weapon.attack(directionTo(player).nor());
    }
  }

  @Override
  protected void interactBehavior(Interactable target, boolean isStart) {
    
  }



}
