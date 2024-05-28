package inf112.skeleton.app.event;

import java.util.ArrayList;

public class MockEventListener implements EventListener {
    protected ArrayList<Event> eventsRegistered;

    protected MockEventListener() {
        this(new ArrayList<>());
    }

    protected MockEventListener(ArrayList<Event> eventsRegistered) {
        this.eventsRegistered = eventsRegistered;
    }

    @Override
    public <E extends Event> void callback(E event) {
        eventsRegistered.add(event);
    }

}
