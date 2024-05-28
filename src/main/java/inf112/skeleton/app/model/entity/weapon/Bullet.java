package inf112.skeleton.app.model.entity.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import inf112.skeleton.app.model.entity.powerup.PowerUp;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.alien.Alien;
import inf112.skeleton.app.model.entity.health.HealthAble;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.mapfactories.Tile;
import inf112.skeleton.app.model.mapfactories.TileType;
import inf112.skeleton.app.model.box2d.BodyFactory;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.view.Media;

public class Bullet extends Entity implements Interactable {


    private int damage;
    private float range;
    private float traveledDistance;
    private Vector2 direction;

    // The bullet's dimensions for rendering
    public final static float HEIGHT = 0.5f;
    public final static float WIDTH = 0.1f;

    // The bullet's dimensions for collision detection
    public final static float bodyWidth = 0.5f;
    public final static float bodyHeight = 0.1f;

    private Class<?> ownerType;

    public Bullet(Vector2 position, Vector2 direction, float d, int damage, float e, Class<?> ownerType) {
        super(position, bodyWidth, bodyHeight, null, null, Media.red);
        this.damage = damage;
        this.range = e;
        this.traveledDistance = 0f;
        this.direction = direction;
        this.position = position;
        this.ownerType = ownerType;


        // Use the BodyFactory to create a dynamic body for the bullet
        this.sensorBody = BodyFactory.createSensorBodyRectangle(bodyWidth, bodyHeight, position, BodyDef.BodyType.DynamicBody);

        // Set the bullet's velocity
        sensorBody.setLinearVelocity(direction.nor().scl(d));

        // Set the bullet's initial rotation
        sensorBody.setTransform(sensorBody.getPosition(), direction.angleRad());


        // Store a reference to this Bullet instance in the body's user data for collision handling
        sensorBody.setUserData(this);

        Box2DWorldStatic.addEntity(this.hashCode(), this);
    }


    @Override
    public void update(float deltaTime) {
        // Check if the bullet has travelled passed its range.
        // If so, destroy it.
        Vector2 velocity = sensorBody.getLinearVelocity();
        traveledDistance += velocity.len() * deltaTime / 1000;
        if (traveledDistance > range) {
                Box2DWorldStatic.removeBody(this.sensorBody);
        }
    }


    @Override
    public Vector2 getPosition() {
        return sensorBody.getPosition();
    }

    /**
     * Returns the damage dealt by the bullet.
     * @return 
     */

    public int getDamage() {
        return damage;
    }

    /**
     * Returns the texture of the bullet.
     * @return
     */

    public Texture getPrimaryTexture() {
        return Media.red;
    }

    /**
     * Returns the direction of the bullet.
     * @return
     */

    public static float getDirection(Entity bullet) {
        if (bullet instanceof Bullet) {
            return ((Bullet) bullet).direction.angleDeg();
        } 
        throw new IllegalArgumentException("The entity is not a bullet");
    }

    @Override
    public void interact(Interactable target, boolean isStart) {
        if (isStart) {

            if (target instanceof Bullet) {
                Box2DWorldStatic.removeBody(this.sensorBody);
            }

            if (target instanceof Tile tile) {
                if (tile.getTiletype() == TileType.WALL) {
                    Box2DWorldStatic.removeBody(this.sensorBody);
                }
            }
            
            if (Alien.class.isAssignableFrom(target.getOwnerType()) && Alien.class.isAssignableFrom(this.ownerType)) {
                return;
            }

            if (target.getClass().equals(PowerUp.class)) {
                return;
            }

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