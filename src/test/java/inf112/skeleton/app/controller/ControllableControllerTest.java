package inf112.skeleton.app.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Integration tests for GameController.
 */
public class ControllableControllerTest {

    private GameController gameController;
    private ControllableModel model;

    /**
     * Static method run before everything else
     */
    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationListener() {
            @Override
            public void create() {}
            @Override
            public void resize(int width, int height) {}
            @Override
            public void render() {}
            @Override
            public void pause() {}
            @Override
            public void resume() {}
            @Override
            public void dispose() {}
        };
        new HeadlessApplication(listener, config);
        Gdx.input = Mockito.mock(Input.class);
    }

    /**
     * Setup method called before each of the test methods
     */
    @BeforeEach
    void setUpBeforeEach() {
        model = Mockito.mock(ControllableModel.class);
        gameController = new GameController(model);
    }

    /**
     * Test that pressing W calls moveDirection with up.
     */
    @Test
    void whenWPressed_moveDirectionCalledWithUp() {
        gameController.keyDown(Input.Keys.W);
        verify(model).moveDirection(0, 1);
    }

    /**
     * Test that pressing S calls moveDirection with Down.
     */
    @Test
    void whenWPressed_moveDirectionCalledWithDown() {
        gameController.keyDown(Input.Keys.S);
        verify(model).moveDirection(0, -1);
    }

        /**
     * Test that pressing A calls moveDirection with left.
     */
    @Test
    void whenAPressed_moveDirectionCalledWithLeft() {
        gameController.keyDown(Input.Keys.A);
        verify(model).moveDirection(-1, 0);
    }

    /**
     * Test that pressing D calls moveDirection with right.
     */
    @Test
    void whenDPressed_moveDirectionCalledWithRight() {
        gameController.keyDown(Input.Keys.D);
        verify(model).moveDirection(1, 0);
    }

    @Test
    void testAttackKeyRight() {
        gameController.keyDown(Input.Keys.RIGHT);
        verify(model).attackDirection(1, 0);


    }

    @Test
    void testAttackKeyLeft() {
        gameController.keyDown(Input.Keys.LEFT);
        verify(model).attackDirection(-1, 0);
    }

    @Test
    void testAttackKeyUp() {
        gameController.keyDown(Input.Keys.UP);
        verify(model).attackDirection(0, 1);
    }

    @Test
    void testAttackKeyDown() {
        gameController.keyDown(Input.Keys.DOWN);
        verify(model).attackDirection(0, -1);
    }


}