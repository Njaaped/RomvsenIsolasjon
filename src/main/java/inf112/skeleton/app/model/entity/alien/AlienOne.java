package inf112.skeleton.app.model.entity.alien;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.weapon.Cooldown;
import inf112.skeleton.app.model.entity.weapon.Weapon;
import inf112.skeleton.app.model.entity.weapon.weapons.Hand;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.view.Media;

public class AlienOne extends Alien {
  private static final double DEFAULT_SPEED = 3;
  private static final float WIDTH = 1f;
  private static final float HEIGHT = 1f;
  private static final int POINT_VALUE = 10;
  private Weapon weapon;

  public AlienOne(Vector2 pos) {
    super(
      pos,
      WIDTH,
      HEIGHT,
      DEFAULT_SPEED,
      POINT_VALUE,
      Media.alien
    );
    this.weapon = new Hand(this.physicalBody, this.getClass());
  }
  
  @Override
  protected void updateBehavior(float deltaTime) {
    if (weapon instanceof Cooldown cooldownWeapon) {
      cooldownWeapon.reduceCooldown();
    }
    double dist = distanceTo(Box2DWorldStatic.getPlayer());
    if (dist < 1d) {
      this.weapon.attack(directionTo(Box2DWorldStatic.getPlayer()).nor());
    }
  }

  @Override
  protected void interactBehavior(Interactable target, boolean isStart) {
    
  }

}
