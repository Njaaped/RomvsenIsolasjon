package inf112.skeleton.app.event;

import java.util.HashMap;

/**
 * Static class for getting different event-emitters.
 * <p>
 * RESERVED CHANNELS
 * <ul>
 *  <li>1 - Main channel</li>
 * </ul>
 */
public class GlobalEventBus {
    protected static HashMap<Integer, EventEmitter> eventChannels;

    /**
     * Get an event bus.
     * @param channel Channel id of the event bus.
     * @return The event bus belonging to the given channel.
     */
    public static IEventBus getInstance(int channel) {
        if (eventChannels == null) {
            eventChannels = new HashMap<>();
        }
        if (!eventChannels.containsKey(channel)) {
            eventChannels.put(channel, new EventEmitter());
        }

        return eventChannels.get(channel);
    }
}
