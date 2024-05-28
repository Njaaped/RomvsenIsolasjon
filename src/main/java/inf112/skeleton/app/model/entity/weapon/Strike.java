package inf112.skeleton.app.model.entity.weapon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.health.HealthAble;
import inf112.skeleton.app.model.box2d.BodyFactory;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.view.Media;

public class Strike extends Entity implements Interactable {
  private Body parentBody;
  protected Class<?> ownerType;
  private int damage;
  private float duration;
  private Vector2 direction;

  public Strike(Body parentBody, Vector2 position, Vector2 direction, float size, int damage, float duration, Class<?> ownerType) {
    this.width = this.height = size;
    this.damage = damage;
    this.duration = duration;
    this.direction = direction;
    this.position = position;
    this.primaryTexture = Media.green;
    this.parentBody = parentBody;
    this.ownerType = ownerType;

    this.sensorBody = BodyFactory.createSensorBodyRound(width, height, setStrikePos(), BodyDef.BodyType.DynamicBody);
    Box2DWorldStatic.addEntity(this.hashCode(), this);

    sensorBody.setUserData(this);

  }

  private Vector2 setStrikePos() {
    Vector2 strikePosition = parentBody.getPosition().cpy();
    strikePosition.add(new Vector2(-width/2, -height/2).add(direction.scl(.5f)));
    return strikePosition;
  }

//  private void weldToParent() {
//    WeldJoint()
//
//  }

  @Override
  public Vector2 getPosition() {
    return sensorBody.getPosition();
  }

  @Override
  public void update(float deltaTime) {
    duration = duration - deltaTime;

    if (duration <= 0) {
        Box2DWorldStatic.removeBody(this.sensorBody);
    }
  }


  @Override
  public void interact(Interactable target, boolean isStart) {
    if (isStart) {
      if (target.getOwnerType() == this.ownerType) {
        return;
      }
      if (target instanceof HealthAble damageableTarget) {
        damageableTarget.changeHealth(-this.damage);
      }
        Box2DWorldStatic.removeBody(this.sensorBody);
    }
  }

  @Override
  public Class<?> getOwnerType() {
    return this.ownerType;
  }
}
