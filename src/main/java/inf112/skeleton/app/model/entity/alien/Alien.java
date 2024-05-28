package inf112.skeleton.app.model.entity.alien;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;
import inf112.skeleton.app.model.box2d.BodyFactory;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.mapfactories.maputils.AStar;
import inf112.skeleton.app.model.score.ScoreEvent;


// Base class for all Aliens
public abstract class Alien extends Entity implements Interactable {
    protected double speed;
    protected boolean seen;
    protected int pathCounter;
    protected List<AStar.Node> path;
    protected static final float REACH_THRESHOLD = 0.05f;
    protected int pointValue;

    public Alien(Vector2 pos, float width, float height, double defaultSpeed, int pointValue, Texture texture) {
        super(
            pos,
            width,
            height,
            BodyFactory.createPhysicalBodyRound(width / 1.3f, height / 1.3f, pos, BodyDef.BodyType.DynamicBody),
            null,
            texture
        );
        Box2DWorldStatic.addEntity(this.hashCode(), this);
        this.speed = defaultSpeed;
        this.pointValue = pointValue;
        this.seen = false;
        this.pathCounter = 0;
        this.path = new ArrayList<>();
    }

    protected void walkTowards(Entity entity) {
        Vector2 currentPos = getPosition().cpy();
    
        if (canSee(entity)){
          Vector2 targetPos = entity.getPosition().cpy();
          Vector2 movementDirection = (targetPos.add(currentPos.scl(-1))).nor();
          this.physicalBody.setLinearVelocity(movementDirection.scl((float) this.speed));
          return;
        }
        if (pathCounter == 0) {
          calculatePath(entity);
        }
        pathCounter++;
        if (pathCounter > 500) {
          pathCounter = 0;
        }
        if (path == null) {
          throw new RuntimeException("Path is null");
        }
        if (path.size() == 0) {
          calculatePath(entity);
          if (path.size() == 0) {
            return;
          }
        }
        AStar.Node nextNode = path.get(0);
        float centerRow = nextNode.getRow() + 0.5f;
        float centerCol = nextNode.getCol() + 0.5f;
        Vector2 nextNodePos = new Vector2(centerCol, centerRow);
        Vector2 movementDirection = (nextNodePos.cpy().add(currentPos.cpy().scl(-1))).nor();
        this.physicalBody.setLinearVelocity(movementDirection.scl((float) this.speed));
        if (currentPos.dst(nextNodePos) < REACH_THRESHOLD) {
          path.remove(0);
        } 
      } 
    
      protected void calculatePath(Entity entity) {
        int posCol = (int) getPosition().x;
        int posRow = (int) getPosition().y;
        int targetCol = (int) entity.getPosition().x;
        int targetRow = (int) entity.getPosition().y;
        int[][] map = Box2DWorldStatic.intmap;
        path = AStar.algorithm(map, posRow, posCol, targetRow, targetCol);
        if (path == null) {
          return;
        }
        if (path.size() == 0) {
          return;
        }
        path.remove(0);
      }
    
      protected double distanceTo(Entity entity) {
        Vector2 targetPos = entity.getPosition().cpy();
        Vector2 currentPos = getPosition().cpy();
        double targetLength = (targetPos.add(currentPos.scl(-1))).len();
        return targetLength;
      }
    
      protected Vector2 directionTo(Entity entity) {
        Vector2 targetPos = entity.getPosition().cpy();
        Vector2 currentPos = getPosition().cpy();
        Vector2 directionVector = targetPos.add(currentPos.scl(-1));
        return directionVector;
      }
    
      protected boolean canSee(Entity entity) {
        final boolean[] canSee = {true}; 
    
        Vector2 targetPosition = entity.getPosition().cpy();
        Vector2 currentPosition = getPosition().cpy();
    
        Box2DWorldStatic.world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
              Object userData = fixture.getBody().getUserData();
              if (!(userData instanceof Player)) {
                canSee[0] = false;
                return 0;
              }
              return fraction;
            }
        }, currentPosition, targetPosition); 
    
        return canSee[0];
      }

    public void defeated() {
        IEventBus bus = GlobalEventBus.getInstance(1);
        bus.trigger("EnemyDefeated", new ScoreEvent(this, this.pointValue));
        dispose();
    }


    @Override
    public void update(float deltaTime) {
          Player player = (Player) Box2DWorldStatic.getPlayer();
      
          double dist = distanceTo(player);
      
          if (dist < 15d) {
            if (!seen) {
              if (canSee(player)) {
                seen = true;
              }
            } else {
              walkTowards(player);
            }
          } else {
            this.physicalBody.setLinearVelocity(Vector2.Zero);
          }
        updateBehavior(deltaTime);
    }

    @Override
    public void changeHealth(float damage) {
        defeated();
    }

    @Override
    public void interact(Interactable target, boolean isStart) {
      interactBehavior(target, isStart);
    }

    @Override 
    public Class<?> getOwnerType() {
        return this.getClass();
    }
    
    /**
     * Updates the alien for specific alien
     * @param deltaTime
     */
    protected abstract void updateBehavior(float deltaTime);

    /**
     * Interacts with target for specific alien
     * @param target
     * @param isStart
     */

    protected abstract void interactBehavior(Interactable target, boolean isStart);
}