package inf112.skeleton.app.model.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * This class is responsible for creating physical bodies and sensor bodies within a Box2D world.
 * It provides static factory methods to create bodies with predefined properties.
 */
public class BodyFactory {

    /**
     * Creates and returns a physical Body in the Box2D world with specified dimensions, position, and type.
     * The created body has fixed rotation and no restitution.
     * The created body is a rectangle
     *
     * @param width The width of the body.
     * @param height The height of the body.
     * @param pos The initial position of the body's top left corner in the world.
     * @param bodyType The type of the body (dynamic, static, or kinematic).
     * @return The created Body instance with a box shape.
     */
    public static Body createPhysicalBodyRectangle(float width, float height, Vector2 pos, BodyDef.BodyType bodyType){
        Body body = createBodyFromDef(width, height, pos, bodyType);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0;

        body.createFixture(fixtureDef);
        shape.dispose();
        

        return body;
    }

    /**
     * Creates and returns a physical Body in the Box2D world with specified dimensions, position, and type.
     * The created body has fixed rotation and no restitution.
     * The created body is a circle
     *
     * @param width The width of the body.
     * @param height The height of the body.
     * @param pos The initial position of the body's center in the world.
     * @param bodyType The type of the body (dynamic, static, or kinematic).
     * @return The created Body instance with a box shape.
     */
    public static Body createPhysicalBodyRound(float width, float height, Vector2 pos, BodyDef.BodyType bodyType){
        Body body = createBodyFromDef(width, height, pos, bodyType);

        CircleShape shape = new CircleShape();
        shape.setRadius((width+height)/4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0;

        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    /**
     * Creates and returns a sensor Body in the Box2D world with specified dimensions, position, and type.
     * The created body has fixed rotation, no restitution, and the fixture is marked as a sensor.
     *
     * @param width The width of the body.
     * @param height The height of the body.
     * @param pos The initial position of the body's center in the world.
     * @param bodyType The type of the body (dynamic, static, or kinematic).
     * @return The created Body instance with a box shape, marked as a sensor.
     */
    public static Body createSensorBodyRectangle(float width, float height, Vector2 pos, BodyDef.BodyType bodyType){
        Body body = createBodyFromDef(width, height, pos, bodyType);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;


        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }


    /**
     * Creates and returns a sensor Body in the Box2D world with specified dimensions, position, and type.
     * The created body has fixed rotation, no restitution, and the fixture is marked as a sensor.
     *
     * @param width The width of the body.
     * @param height The height of the body.
     * @param pos The initial position of the body's center in the world.
     * @param bodyType The type of the body (dynamic, static, or kinematic).
     * @return The created Body instance with a box shape, marked as a sensor.
     */
    public static Body createSensorBodyRound(float width, float height, Vector2 pos, BodyDef.BodyType bodyType){
        Body body = createBodyFromDef(width, height, pos, bodyType);

        CircleShape shape = new CircleShape();
        shape.setRadius((width+height)/4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    private static Body createBodyFromDef(float width, float height, Vector2 pos, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + width/2, pos.y + height/2);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = bodyType;

        return Box2DWorldStatic.world.createBody(bodyDef);
    }
}
