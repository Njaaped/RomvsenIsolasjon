package inf112.skeleton.app.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class FactoryTest {
    private Factory<Product> factory;

    @BeforeEach
    public void init() {
        this.factory = new Factory<>();
    }

    @Test
    public void sanityTest() {
        Producer<Product> producer = new MockProducer();
        factory.addProducer("TEST", producer);

        Product product = factory.create("TEST");
        assertFalse(product == null);
    }

    @Test
    public void takesLambda() {
        factory.addProducer("TEST", (args) -> new Product(args));
        Product product = factory.create("TEST");
        assertFalse(product == null);
    }

    @Test
    public void producesDiffrent() {
        factory.addProducer("TEST", (args) -> new Product(args));
        Product product1 = factory.create("TEST");
        Product product2 = factory.create("TEST");
        assertFalse(product1 == product2);
    }

    @Test
    public void takesArgument() {
        int val = 1;
        Map<String, Object> testArgs = Map.of("arg", val);
        factory.addProducer("TEST", (args) -> new Product(args));
        int passedVal = (int) factory.create("TEST", testArgs).args.get("arg");
        assertTrue(passedVal == val);
    }
}
