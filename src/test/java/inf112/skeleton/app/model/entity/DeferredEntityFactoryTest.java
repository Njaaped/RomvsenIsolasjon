package inf112.skeleton.app.model.entity;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.MockEvent;
import inf112.skeleton.app.factory.Factory;

public class DeferredEntityFactoryTest {

    @Test
    public void getsSameInstance() {
        DeferredEntityFactory f1 = DeferredEntityFactory.getInstance();
        DeferredEntityFactory f2 = DeferredEntityFactory.getInstance();
        assertTrue(f1 == f2);
    }

    @Test
    public void createsEntityAfterStep() {
        Factory<Entity> mockFactory = new Factory<>();
        mockFactory.addProducer("TEST", (args) -> {
            return new MockEntity();
        });

        DeferredEntityFactory f = new DeferredEntityFactory(mockFactory);
        f.createEntity("TEST", Vector2.Zero);
        assertTrue(MockEntity.getCreationCount() == 0);

        GlobalEventBus.getInstance(1).trigger("stepComplete", new MockEvent(null));
        assertTrue(MockEntity.getCreationCount() == 1);

        // Make sure it dosen't create entity multiple times
        GlobalEventBus.getInstance(1).trigger("stepComplete", new MockEvent(null));
        assertTrue(MockEntity.getCreationCount() == 1);
    }
}
