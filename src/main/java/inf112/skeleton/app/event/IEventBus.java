package inf112.skeleton.app.event;

public interface IEventBus {
    /**
     * Register an event on the event bus
     * @param <E> The type of the event to register. The name of the event will determine
     * @param eventName Name of the event the listener will listen to
     * @param listener A listener that will be called once the event has happend.
     */
    public void on(String eventName, EventListener listener);

    /**
     * Trigger an event on this event bus.
     * @param eventName Name of the event to trigger.
     * @param event Information about the event.
     */
    public void trigger(String eventName, Event event);

    /**
     * Removes a listener from an event
     * @param eventName Name of the event to remove the listener from.
     * @param listener The object that should stop listening to the event.
     */
    public void remove(String eventName, EventListener listener);
}
