package inf112.skeleton.app.model.box2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;

import inf112.skeleton.app.model.mapfactories.ITileBodyCreator;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.MapEntry;

import java.util.Collection;
import java.util.HashSet;

public class Box2DWorldStatic {
  public static World world;
  public static ConcurrentHashMap<Integer, Entity> entityMap;
  public static HashSet<Body> tileBodies;
  static HashSet<Body> bodiesToBeRemoved;
  static Box2DDebugRenderer debugRenderer;
  public static int entityId;
  public static int playerID;
  public static int[][] intmap;


  /**
   * Constructor for creating a new Box2DWorld instance.
   * Initializes the physics world with no gravity and sets a contact listener to handle collisions.
   */
  public static void startBox2DWorld() {
    world = new World(new Vector2(0, 0), true);
    bodiesToBeRemoved = new HashSet<>();
    entityMap = new ConcurrentHashMap<>();
    tileBodies = new HashSet<>();
    entityId = 1;
    playerID = 0;
    world.setContactListener(new ContactListener() {
      @Override
      public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        handleCollisions(A, B, true);
      }

      @Override
      public void endContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        handleCollisions(A, B, false);
      }

      @Override
      public void preSolve(Contact contact, Manifold manifold) {

      }

      @Override
      public void postSolve(Contact contact, ContactImpulse contactImpulse) {

      }
    });
  }

  /**
   * Advances the physics simulation of the world by a time step.
   * Also clears the forces in the world after stepping.
   */
  public static void stepWorld(float deltaTime){
    Collection<Integer> entityKeys = entityMap.keySet();
    for (int entityKey: entityKeys) {
      Entity entity = entityMap.get(entityKey);
      entity.update(deltaTime);
    }
    world.step(deltaTime, 6, 2);
    world.clearForces();
    destroyBodies();
    GlobalEventBus.getInstance(1).trigger("stepComplete", new Event(null));
  }

  /**
   * Destroys all bodies in the bodiesToBeRemoved set.
   */
  private static void destroyBodies() {
    if (!bodiesToBeRemoved.isEmpty()) {
      for (Body b : bodiesToBeRemoved) {
        removeEntity(b.getUserData().hashCode());
        world.destroyBody(b);
      }
      bodiesToBeRemoved.clear();
    }

  }

  /**
   * Handles collisions between entities, specifically for sensor interactions.
   * Normal collisions are managed automatically by the world.step method.
   * Only handles sensors, Normal collisons are handled by the world.step method.
   *
   * @param fixtureA The first fixture involved in the collision.
   * @param fixtureB The second fixture involved in the collision.
   * @param isStart Indicates whether this is the beginning of the collision (true) or the end (false).
   */
  public static void handleCollisions(Fixture fixtureA, Fixture fixtureB, boolean isStart){
    Object A = fixtureA.getBody().getUserData();
    Object B = fixtureB.getBody().getUserData();
    if(A != null && B != null) {
      if (A instanceof Interactable objA && B instanceof Interactable objB) {
        objA.interact(objB, isStart);
        objB.interact(objA, isStart);
      }
    }
  }

  /**
   * Adds a single entity to the entity map.
   *
   * @param hashCode Unique identifier for the entity.
   * @param entity   The entity object to add.
   */
  public static void addEntity(int hashCode, Entity entity){
    entityMap.put(hashCode, entity);
  }

  /**
   * Removes an entity from the entity map based on its hash code.
   *
   * @param hashCode Unique identifier for the entity to be removed.
   */

  private static void removeEntity(int hashCode){
    entityMap.remove(hashCode);
  }

  /**
   * Retrieves all entities currently managed in the entity map.
   *
   * @return An Array containing all entities.
   */
  public static Array<Entity> getEntities(){
    Array<Entity> entities = new Array<>();
    for(Entity entity : entityMap.values()){
      entities.add(entity);
    }
    return entities;
  }

  /**
   * Draws hitboxes for debugging purposes using the provided camera.
   *
   * @param camera The camera to use for rendering the debug view.
   */
  public static void drawHitboxes(OrthographicCamera camera) {
    if (debugRenderer == null) {
      debugRenderer = new Box2DDebugRenderer();
    }
    debugRenderer.render(world, camera.combined);
  }

  /**
   * Marks a body for removal from the physics world.
   *
   * @param physicalBody The body to be removed.
   */
  public static void removeBody(Body physicalBody) {
    bodiesToBeRemoved.add(physicalBody);
  }

  /**
   * Generates and returns a new unique entity ID.
   *
   * @return A new unique identifier for an entity.
   */
  public static int setEntityId() {
    entityId = entityId+1;
    return entityId;
  }

  /**
   * Populates the physics world with tile bodies based on the given map.
   *
   * @param map The game map containing tile data.
   * @param tileBodyCreator The interface to create physical bodies for tiles.
   */
  public static void loadMap(Map map, ITileBodyCreator tileBodyCreator) {
    for (MapEntry tile : map) {
      Vector2 tilePosition =
      new Vector2(map.getTileSize() * tile.col(), map.getTileSize() * tile.row());
      tileBodyCreator.createTileBody(tileBodies, tile.tileType(), tilePosition, map.getTileSize());
    }
    Box2DWorldStatic.intmap = map.getIntMap();
  }

   /**
   * Retrieves the player entity from the entity map, identifying it by the player ID.
   * 
   * @return The player entity if found; otherwise, null.
   */
  public static Entity getPlayer() {
    if (playerID == 0) {
      Collection<Integer> entityKeys = entityMap.keySet();
      for (int entityKey : entityKeys) {
        if (entityMap.get(entityKey) instanceof Player player) {
          playerID = player.hashCode();
          return player;
        }
      }
    }
    try {
      return entityMap.get(playerID);
    } catch (Exception e) {
      return null;
    }
  }
}
