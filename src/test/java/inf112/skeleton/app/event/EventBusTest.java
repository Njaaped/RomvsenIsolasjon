package inf112.skeleton.app.event;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class EventBusTest {
    @Test
    public void notifiesListeners() {
        MockEventListener listener = new MockEventListener();
        EventEmitter emitter = new EventEmitter();
        emitter.on("TEST", listener);

        Event event = new Event(this);
        emitter.trigger("TEST", event);

        assertTrue(listener.eventsRegistered.size() == 1);
        assertTrue(listener.eventsRegistered.get(0) == event);
    }

    @Test
    public void notifiesCorrectListeners() {
        MockEventListener listener1 = new MockEventListener();
        MockEventListener listener2 = new MockEventListener();
        EventEmitter emitter = new EventEmitter();
        emitter.on("TEST", listener1);
        emitter.on("NOT_TEST", listener2);

        Event event = new Event(this);
        emitter.trigger("TEST", event);

        assertTrue(listener1.eventsRegistered.size() == 1);
        assertTrue(listener2.eventsRegistered.size() == 0);
    }

    @Test
    public void removesListeners() {
        MockEventListener listener = new MockEventListener();
        EventEmitter emitter = new EventEmitter();
        emitter.on("TEST", listener);

        Event event = new Event(this);
        emitter.trigger("TEST", event);
        assertTrue(listener.eventsRegistered.size() == 1);

        emitter.remove("TEST", listener);
        emitter.trigger("TEST", event);
        assertTrue(listener.eventsRegistered.size() == 1);
    }

    @Test
    public void noListenersRegistered() {
        EventEmitter emitter = new EventEmitter();
        Event event = new Event(this);
        try {   
            emitter.trigger("TEST", event);
        } catch (Exception e) {
            assertTrue("Event-trigger threw an error", false);
        }
    }
}
