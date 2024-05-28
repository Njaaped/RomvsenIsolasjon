package inf112.skeleton.app.model.entity.powerup;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.event.EventListener;
import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.model.entity.DeferredEntityFactory;
import inf112.skeleton.app.model.entity.Entity;

public class LootDropper implements ILootDropper {
    private Random random;
    private final String[] items = new String[]{"Star", "HealthPacket", "RayGunPickup"};

    public LootDropper() {
        random = new Random();
        GlobalEventBus.getInstance(1).on("EnemyDefeated", new EventListener() {
            @Override
            public <E extends Event> void callback(E event) {
                if (event.getCaller() instanceof Entity enemy) {
                    Vector2 dropLocation = enemy.getPosition();
                    dropItem(dropLocation);
                }
            }
        });
    }

    public LootDropper(int seed) {
        random = new Random(seed);
    }

    @Override
    public void dropItem(Vector2 position) {
        boolean shouldDrop = random.nextInt(10) == 0;
        if (!shouldDrop) {
            return;
        }
        
        int rIndex = random.nextInt(items.length);
        String item = items[rIndex];
        DeferredEntityFactory.getInstance().createEntity(item, position);
    }
}
