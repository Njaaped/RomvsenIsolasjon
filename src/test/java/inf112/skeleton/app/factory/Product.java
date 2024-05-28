package inf112.skeleton.app.factory;

import java.util.Map;

public class Product {
    public Map<String, Object> args;

    protected Product(Map<String, Object> args) {
        this.args = args;
    }
}
