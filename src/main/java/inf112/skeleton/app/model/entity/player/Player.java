package inf112.skeleton.app.model.entity.player;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.alien.Alien;
import inf112.skeleton.app.model.entity.health.Health;
import inf112.skeleton.app.model.entity.powerup.PowerUpAble;
import inf112.skeleton.app.model.entity.powerup.effects.PowerUpEffect;
import inf112.skeleton.app.model.entity.weapon.Weapon;
import inf112.skeleton.app.model.entity.weapon.weapons.Gun;
import inf112.skeleton.app.model.box2d.BodyFactory;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.model.entity.weapon.Cooldown;


public class Player extends Entity implements Interactable, PowerUpAble {
    private static final float DEFAULT_SPEED = 5;
    private static final float WIDTH = 1;
    private static final float HEIGHT = 1;
    private Vector2 movementDirection = Vector2.Zero;
    private float speed;
    private boolean attacking = false;
    private Vector2 attackDirection = Vector2.Zero;
    private Weapon weapon;
    private Queue<PowerUpEffect> powerUps;


    public Player(Vector2 position) {
        super(
            position,
            WIDTH,
            HEIGHT,
            BodyFactory.createPhysicalBodyRound(WIDTH/2, HEIGHT/2, position, BodyDef.BodyType.DynamicBody),
            null,
            Media.player
        );
        Box2DWorldStatic.addEntity(this.hashCode(), this);
        this.speed = DEFAULT_SPEED;
        this.health = new Health(100);
        this.weapon = new Gun(this.physicalBody, this.getClass());
        this.powerUps = new ConcurrentLinkedQueue<>();

    }

    @Override
    public void update(float deltaTime) {

        updatePowerUps(deltaTime);

        this.physicalBody.setLinearVelocity(movementDirection.cpy().scl(speed));

        if (attacking) {
            weapon.attack(attackDirection.cpy());
        }

        if (weapon instanceof Cooldown cooldownWeapon) {
            cooldownWeapon.reduceCooldown();
        }

    }

    private void updatePowerUps(float deltaTime) {
        Set<Class<?>> powerUpsToSkip = new HashSet<>();
        for (PowerUpEffect powerUp : powerUps) {
            if (powerUpsToSkip.contains(powerUp.getClass())) {
                continue;
            }
            powerUp.update(this, deltaTime);
            powerUpsToSkip.add(powerUp.getClass());
        }
    }  

    /**
     * Sets the movement direction of the player
     * @param direction
     */

    public void setMovementDirection(Vector2 direction) {
        this.movementDirection = direction.nor();
    }

    /**
     * Returns the movement direction of the player
     * @return
     */
    
    public Vector2 getMovementDirection() {
        return this.movementDirection;
    }

    /**
     * resets the speed of the player
     */
    public void resetSpeed() {
        this.speed = DEFAULT_SPEED;
    }

    /**
     * Increases the speed of the player
     * @param amount the amount to increase the speed by
     */

    public void changeSpeed(float amount) {
        if (imunity) {
            return;
        }
        this.speed = DEFAULT_SPEED * amount;
    }

    /**
     * Returns the speed of the player
     * @return the speed of the player
     */

    public float getSpeed(){
        return this.speed;
    }

    /**
     * Sets the player to sprinting
     * @param sprinting
     */

    public void setSprintState(boolean sprinting) {
        if (imunity) {
            return;
        }
        this.speed = sprinting ? DEFAULT_SPEED * 2 : DEFAULT_SPEED;
    }

    /**
     * Sets the player to attacking
     * @param b
     * @param v
     */

    public void setAttacking(boolean b, Vector2 v) {
        this.attacking = b;
        this.attackDirection = v;
        return;
    }

    /**
     * Returns the weapon of the player
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     *  gets the weapon
     * @return the weapon
     */
    public Weapon getWeapon() {
        return this.weapon;
    }

    @Override
    public void addPowerUp(PowerUpEffect powerUp) {
        powerUps.add(powerUp);
        powerUp.activate(this);
    }

    
    @Override
    public void removePowerUp(PowerUpEffect powerUp) {
        powerUps.remove(powerUp);
        for (PowerUpEffect p : powerUps) {
            if (p.getClass().equals(powerUp.getClass())) {
                return;
            }
        }
        powerUp.deactivate(this);
    }

    @Override
    public void interact(Interactable target, boolean isStart) {
        if (target instanceof Alien) {
            if (imunity) {
                Alien alien = (Alien) target;
                alien.defeated();
            }
        }
    }

    @Override
    public Class<?> getOwnerType() {
        return this.getClass();
    }

}
