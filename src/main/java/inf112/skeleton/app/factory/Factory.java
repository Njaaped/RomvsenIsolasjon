package inf112.skeleton.app.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * A generic factory class for creating objects of type T. This class allows registration of multiple producers,
 * each capable of producing objects of type T, and manages a set of arguments that can be used by these producers.
 *
 * @param <T> the type of objects this factory creates
 */
public class Factory<T> {
    private final HashMap<String, Producer<T>> producers; // A map of producer names to their respective Producer instances

    /**
     * Constructs a new Factory instance with specified maps for producers and arguments.
     *
     * @param producers a map of producer names to their respective Producer instances
     * @param args a map of argument names to their values
     */
    private Factory(HashMap<String, Producer<T>> producers) {
        this.producers = producers;
    }

    /**
     * Constructs an empty Factory with no producers and no arguments.
     */
    public Factory() {
        this(new HashMap<>());
    }

    /**
     * Adds a producer to the factory.
     *
     * @param name the name that identifies the producer
     * @param producer the producer that creates an object of type T
     */
    public void addProducer(String name, Producer<T> producer) {
        this.producers.put(name, producer);
    }

    /**
     * Creates an object of type T using the specified producer.
     *
     * @param name the name of the producer to use for creating the object
     * @return an instance of type T produced by the specified producer, or null if no producer is found for the name
     */
    public T create(String name) {
        return create(name, Map.of());
    }

    /**
     * Creates an object of type T using the specified producer.
     *
     * @param name the name of the producer to use for creating the object
     * @param args arguments to be passed in to the producer
     * @return an instance of type T produced by the specified producer, or null if no producer is found for the name
     */
    public T create(String name, Map<String, Object> args) {
        Producer<T> producer = producers.get(name);
        if (producer != null) {
            return producer.produce(args);
        }

        return null;
    }
}
