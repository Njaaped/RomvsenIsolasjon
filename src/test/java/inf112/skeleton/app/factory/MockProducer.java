package inf112.skeleton.app.factory;

import java.util.Map;

public class MockProducer implements Producer<Product> {

    @Override
    public Product produce(Map<String, Object> args) {
        return new Product(args);
    }

}
