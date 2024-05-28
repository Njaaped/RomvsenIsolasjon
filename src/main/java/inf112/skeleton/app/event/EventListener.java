package inf112.skeleton.app.event;

/**
 * A listener that can be called when listening for events of type E
 */
@FunctionalInterface
public interface EventListener {
    /**
     * Performs the callback with the given event.
     * @param event Event-object provided by the event emitter.
     */
    public <E extends Event> void callback(E event);
}
