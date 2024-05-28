package inf112.skeleton.app.model.entity.weapon.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;
import inf112.skeleton.app.model.audio.SoundEffectEvent;
import inf112.skeleton.app.model.entity.weapon.BaseWeapon;
import inf112.skeleton.app.model.entity.weapon.Bullet;

public class Gun extends BaseWeapon {
    private int bulletDamage = 10;
    private int bulletVelocity = 20;
    private int bulletRange = 3;

    public Gun(Body parentBody, Class<?> ownerType) {
       super(parentBody, 20, ownerType);
    }

    @Override
    protected void performAttack(Vector2 direction) {
        Vector2 bulletPos = new Vector2(parentBody.getPosition().x - 0.25f + direction.x * 0.25f, parentBody.getPosition().y);
        new Bullet(bulletPos, direction, bulletVelocity, bulletDamage, bulletRange, this.ownerType);
        IEventBus bus = GlobalEventBus.getInstance(2);
        bus.trigger("SoundEffect", new SoundEffectEvent(this, "laser"));
    }
}
