package inf112.skeleton.app.model.entity;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.event.EventListener;
import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;
import inf112.skeleton.app.factory.Factory;
import inf112.skeleton.app.model.entity.powerup.PowerUp;
import inf112.skeleton.app.model.entity.powerup.effects.GodModeEffect;
import inf112.skeleton.app.model.entity.powerup.effects.HealthEffect;
import inf112.skeleton.app.model.entity.powerup.effects.RayGunEffect;
import inf112.skeleton.app.model.entity.alien.*;

import inf112.skeleton.app.view.Media;

public class DeferredEntityFactory {
    private static DeferredEntityFactory instance;
    private Factory<Entity> entityFactory;

    private DeferredEntityFactory() {
        super();
        this.entityFactory = new Factory<>();
        addEntities();
    }

    protected DeferredEntityFactory(Factory<Entity> entiyFactory) {
        super();
        this.entityFactory = entiyFactory;
    }

    private void addEntities() {
        entityFactory.addProducer("Grunt", (var args) -> {
            return new AlienOne((Vector2) args.get("position"));
        });

        entityFactory.addProducer("Shooter", (var args) -> {
            return new AlienTwo((Vector2) args.get("position"));
        });

        entityFactory.addProducer("Bomber", (var args) -> {
            return new AlienBomber((Vector2) args.get("position"), 49);
        });

        // Pick-ups
        entityFactory.addProducer("Star", (var args) -> {
            return new PowerUp((Vector2) args.get("position"), new GodModeEffect(4), Media.star);
        });

        entityFactory.addProducer("HealthPacket", (var args) -> {
            return new PowerUp((Vector2) args.get("position"), new HealthEffect(25), Media.healthPacket);
        });

        entityFactory.addProducer("RayGunPickup", (var args) -> {
            return new PowerUp((Vector2) args.get("position"), new RayGunEffect(4), Media.raygun);
        });
    }

    public static DeferredEntityFactory getInstance() {
        if (instance == null) {
            instance = new DeferredEntityFactory();
        }

        return instance;
    }

    public void createEntity(String entityName, Vector2 position) {
        IEventBus eventBus = GlobalEventBus.getInstance(1);
        String EVENT_NAME = "stepComplete";
        Map<String, Object> args = Map.of("position", position);

        eventBus.on(EVENT_NAME, new EventListener() {
            @Override
            public <E extends Event> void callback(E event) {
                entityFactory.create(entityName, args);
                eventBus.remove(EVENT_NAME, this);
            }
        });
    }
}
