package inf112.skeleton.app.model.entity;

public interface Interactable {

  /**
   * Interact with another interactable object
   *
   * @param target
   * @param isStart
   */
  public void interact(Interactable target, boolean isStart);

  public Class<?> getOwnerType();

  /*

  Interactable:
  use instanceof for handling within classes


   */
}
