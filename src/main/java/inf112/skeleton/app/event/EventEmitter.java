package inf112.skeleton.app.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EventEmitter implements IEventBus {
    // A map with keys being name of events and value being a list of all the listeners to that event
    private HashMap<String, List<EventListener>> eventListeners;

    public EventEmitter() {
        this(new HashMap<>());
    }

    protected EventEmitter(HashMap<String, List<EventListener>> eventListeners) {
        this.eventListeners = eventListeners; 
    }

    @Override
    public void on(String eventName, EventListener listener) {
        List<EventListener> listeners = eventListeners.getOrDefault(eventName, Collections.synchronizedList(new ArrayList<EventListener>()));
        listeners.add(listener);

        if (!eventListeners.containsKey(eventName)) {
            eventListeners.put(eventName, listeners);
        }
    }

    @Override
    public void trigger(String eventName, Event event) {
        List<EventListener> listeners = eventListeners.getOrDefault(eventName, null);

        if (listeners == null) {
            return;
        }

        for (EventListener listener : listeners) {
            listener.callback(event);
        }
    }

    @Override
    public void remove(String eventName, EventListener listener) {
        List<EventListener> listeners = new ArrayList<>(eventListeners.getOrDefault(eventName, new ArrayList<>()));
        for (int i = 0; i < listeners.size(); i++) {
            if (listeners.get(i) == listener) {
                listeners.remove(i);
                break;
            }
        }

        eventListeners.put(eventName, listeners);
    }
}
