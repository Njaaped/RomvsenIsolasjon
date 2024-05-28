package inf112.skeleton.app.model.entity;

public class MockEntity extends Entity {
    private static int creationCount;

    public MockEntity() {
        MockEntity.creationCount += 1;
    }

    public static int getCreationCount() {
        return creationCount;
    }
}
