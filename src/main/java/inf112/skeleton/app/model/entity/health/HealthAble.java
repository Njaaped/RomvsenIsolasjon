package inf112.skeleton.app.model.entity.health;

/**
 * Interface for entities that can take damage.
 */
public interface HealthAble {

  /**
   * Changes the health of the entity.
   *
   * @param damage the amount of health to change
   */
  public void changeHealth(float damage);


  /**
   * Returns the health of the entity.
   *
   * @return the health of the entity
   */
  public float getHealth();

  /**
   * Returns the health of the entity as a percentage.
   *
   * @return the health of the entity as a percentage
   */
  public float getHealthPercentage();
}


