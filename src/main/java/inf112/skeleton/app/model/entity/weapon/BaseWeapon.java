package inf112.skeleton.app.model.entity.weapon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class BaseWeapon implements Weapon, Cooldown {
    protected Body parentBody;
    protected int cooldown;
    protected int cooldownProgress;
    protected Class<?> ownerType;

    public BaseWeapon(Body parentBody, int cooldown, Class<?> ownerType) {
        this.parentBody = parentBody;
        this.cooldown = cooldown;
        this.ownerType = ownerType;
        this.cooldownProgress = 0; 
    }

    @Override
    public void attack(Vector2 direction) {
        if (cooldownProgress < cooldown) {
            return;
        }
        performAttack(direction);
        cooldownProgress = 0;
    }

    protected abstract void performAttack(Vector2 direction);

    @Override
    public void reduceCooldown() {
        if (cooldownProgress < cooldown) {
            cooldownProgress++;
        }
    }
}
