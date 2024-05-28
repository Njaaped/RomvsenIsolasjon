package inf112.skeleton.app.event;

public class Event {
    private Object caller;

    public Event(Object caller) {
        this.caller = caller;
    }

    public Object getCaller() {
        return caller;
    }
}
