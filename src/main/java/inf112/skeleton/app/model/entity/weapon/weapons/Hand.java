package inf112.skeleton.app.model.entity.weapon.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;
import inf112.skeleton.app.model.audio.SoundEffectEvent;
import inf112.skeleton.app.model.entity.weapon.BaseWeapon;
import inf112.skeleton.app.model.entity.weapon.Strike;

public class Hand extends BaseWeapon {
  private int damage = 5;
  private float attackDuration = .5f;
  private float size = .3f;

  public Hand(Body parentBody, Class<?> ownerType) {
    super(parentBody, 30, ownerType);
  }
  
  @Override
  protected void performAttack(Vector2 direction) {
    Vector2 strikePosition = parentBody.getPosition().cpy();
    strikePosition.add(new Vector2(-size/2, -size/2).add(direction.scl(1f)));
    new Strike(parentBody, strikePosition, direction, size, damage, attackDuration, ownerType);
    IEventBus bus = GlobalEventBus.getInstance(2);
    bus.trigger("SoundEffect", new SoundEffectEvent(this, "punch"));
  }


}
