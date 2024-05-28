package inf112.skeleton.app.factory;

import java.util.Map;

/**
 * A functional interface for producing objects of type T.
 *
 * @param <T> the type of object produced
 */
@FunctionalInterface
public interface Producer<T> {
    public T produce(Map<String, Object> args);
}
