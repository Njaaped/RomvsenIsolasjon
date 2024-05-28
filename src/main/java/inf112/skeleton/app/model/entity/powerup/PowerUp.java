package inf112.skeleton.app.model.entity.powerup;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import inf112.skeleton.app.model.box2d.BodyFactory;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.powerup.effects.PowerUpEffect;

public class PowerUp extends Entity implements Interactable {
    private static final float WIDTH = 0.5f;
    private static final float HEIGHT = 0.5f;
    private float interpolation = 0;
    private PowerUpEffect effect;
    private Texture texture;

    public PowerUp(Vector2 position, PowerUpEffect effect, Texture texture) {
        super(
            position,
            HEIGHT,
            WIDTH,
            null,
            BodyFactory.createSensorBodyRound(WIDTH / 1.3f, HEIGHT / 1.3f, position, BodyDef.BodyType.DynamicBody),
            texture
        );
        this.effect = effect;
        this.texture = texture;
        Box2DWorldStatic.addEntity(this.hashCode(), this);
    }

    /**
     * Get the texture of the powerup
     * @return the texture of the powerup
     */

    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Get the effect of the powerup
     * @return the effect of the powerup
     */

    public PowerUpEffect getEffect() {
        return this.effect;
    }

    @Override
    public void update(float deltaTime) {
        // wave function
        Vector2 newpos = new Vector2(getPosition().x, getPosition().y + (float) Math.sin(interpolation) / 100);
        if (interpolation > 2 * Math.PI) {
            interpolation = 0;
        } else {
            interpolation += 0.1;
        }
        setPosition(newpos);
    }

    @Override
    public void interact(Interactable target, boolean isStart) {
        if (target instanceof Player) {
            if (isStart) {
                ((Player) target).addPowerUp(effect);
            }
            Box2DWorldStatic.removeBody(this.sensorBody);
        }
    }

    @Override
    public Class<?> getOwnerType() {
        return this.getClass();
    }
}
