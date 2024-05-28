package inf112.skeleton.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;


public class GameLoopTest {
    private int ticks = 0;

    @Test
    public void correctTicks() throws InterruptedException {
         HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
         HeadlessApplication headlessApplication = new HeadlessApplication(new ApplicationAdapter() {}, config);
         int TICKS_PER_SECOND = 60;
         ControllableModel dummyModel = new ControllableModel() {
             @Override
             public void moveDirection(int x, int y) {}

             @Override
             public void isSprinting(boolean sprinting) {

             }

             @Override
             public void update(float deltaTime) {
                 ticks += 1;
             }

             @Override
             public void toggleDebug() {

             }

             @Override
             public void setAttacking(boolean b) {

             }

             @Override
             public void pauseGame() {

             }

             @Override
             public void gameOver() {

             }

             @Override
             public void restartGame() {

             }

             @Override
             public void stopGame() {

             }

             @Override
             public void attackDirection(int x, int y) {

             }
         };
         GameLoop gameLoop = new GameLoop(dummyModel, 1f / TICKS_PER_SECOND);
         gameLoop.start();
         Thread.sleep(1001);
         gameLoop.cancel();
         // innenfor 30%
         assert(TICKS_PER_SECOND < ticks * (1.3) && TICKS_PER_SECOND > ticks * (0.7));
    } 
}
