package inf112.skeleton.app.model.entity.health;

public class Health {
    private float health;
    public final float maxHealth;

    public Health(float maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    /**
     * changes the player health
     * @param amount
     */

    public void changeHealth(float amount) {
        if (amount == 1_000_000_000f) {
            health = 1_000_000_000f;
            return;
        }
        if (health == 1_000_000_000f) {
            if (amount > -100_000_000f) {
                return;
            }
        }
        health = Math.min(health + amount, maxHealth);
    }

    /**
     * Returns the health of the player
     */

    public float getHealth() {
        return health;
    }


    /**
     * Returns the health of the player
     * @return
     */

    public float getHealthPercentage() {
        return health / maxHealth;
    }
}