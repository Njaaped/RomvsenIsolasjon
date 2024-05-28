package inf112.skeleton.app.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.entity.health.Health;
import inf112.skeleton.app.model.entity.health.HealthAble;

/**
 * Represents a generic entity within the game, such as a player, enemy, or any interactive object.
 * Each entity has physical properties defined by the Box2D physics engine and is associated with a texture for rendering.
 */
public class Entity implements HealthAble {

  protected Vector2 position;
  protected float width; 
  protected float height; 

  protected Body physicalBody; 
  protected Body sensorBody; 

  protected Texture primaryTexture; 

  protected int entityId; 
  protected Health health = null;
  protected boolean imunity = false;

  // Empty constructor for temporary use, intended to be replaced by detailed constructor
  public Entity() {}

  /**
   * Constructs a new Entity with specified characteristics and physics bodies.
   *
   * @param position Initial position of the entity in world coordinates
   * @param width Width of the entity in game units
   * @param height Height of the entity in game units
   * @param physicalBody The main Box2D body used for physical interactions
   * @param sensorBody A Box2D body used for detecting interactions without physics impacts
   * @param primaryTexture The texture used for rendering the entity
   */
  public Entity(Vector2 position, float width, float height, Body physicalBody, Body sensorBody, Texture primaryTexture) {
    this.position = position;
    this.width = width;
    this.height = height;
    this.physicalBody = physicalBody;
    if (physicalBody != null) {
      physicalBody.setUserData(this);
    }
    this.sensorBody = sensorBody;
    if (sensorBody != null) {
      sensorBody.setUserData(this);
    }
    this.primaryTexture = primaryTexture;
    this.entityId = Box2DWorldStatic.setEntityId();
  }

  /**
   * Updates the entity's state. This method should be overridden by subclasses to implement specific behaviors.
   *
   * @param deltaTime The time in seconds since the last update
   */
  public void update(float deltaTime) {};

  /**
   * Gets the current position of the entity from its physical body.
   *
   * @return Current position as a {@link Vector2}
   */
  public Vector2 getPosition() {
    if (this.physicalBody != null) {
      return this.physicalBody.getPosition();
    }
    if (this.sensorBody != null) {
      return this.sensorBody.getPosition();
    }
    return this.position;
  }

  /**
   * Sets the position of the entity in world coordinates.
   *
   * @param position The new position as a {@link Vector2}
   */

  public void setPosition(Vector2 position) {
    if (this.physicalBody != null) {
      this.physicalBody.setTransform(position, 0);
    }
    if (this.sensorBody != null) {
      this.sensorBody.setTransform(position, 0);
    }
    this.position = position;
  }

  /**
   * Returns the width of the entity.
   *
   * @return the width in game units
   */
  public float getWidth() {
    return width;
  }

  /**
   * Returns the height of the entity.
   *
   * @return the height in game units
   */
  public float getHeight() {
    return height;
  }

  /**
   * Returns the physical body associated with the entity for physics calculations.
   *
   * @return {@link Body} the physical body of the entity
   */
  public Body getPhysicalBody() {
    return physicalBody;
  }

  /**
   * Returns the sensor body associated with the entity for event detection.
   *
   * @return {@link Body} the sensor body of the entity
   */
  public Body getSensorBody() {
    return sensorBody;
  }

  @Override
  public int hashCode() {
    return entityId;
  }

  /**
   * Retrieves the primary texture of the entity used for rendering.
   *
   * @return {@link Texture} the primary texture
   */
  public Texture getPrimaryTexture() {
    return primaryTexture;
  }

  /**
   * sets the primary texture of the entity used for rendering.
   * 
   * @param texture the new texture
   */

  public void setPrimaryTexture(Texture texture) {
    this.primaryTexture = texture;
  }

  /**
   * sets imunity to true or false
   * @param imunity true or false
   */
  public void setImunity(boolean imunity) {
    this.imunity = imunity;
  }

  /**
   * Disposes of resources associated with the entity, primarily its texture.
   */
  protected void dispose() {
    Box2DWorldStatic.removeBody(this.physicalBody);
  }

  @Override
  public void changeHealth(float damage) {
    if (health != null) {
      if (damage < 0 && imunity) {
        return;
      }
      health.changeHealth(damage);
    }
  }

  @Override
  public float getHealth() {
    return health.getHealth();
  }

  @Override
  public float getHealthPercentage(){
    return health.getHealthPercentage();
  }

}
