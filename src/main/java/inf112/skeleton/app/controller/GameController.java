package inf112.skeleton.app.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Disposable;

import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.model.gamestate.GameState;

public class GameController extends InputAdapter implements Disposable {
  private final int TICKS_PER_SECOND = 50;
  
  private final ControllableModel model;
  private final GameLoop gameLoop;

  private boolean up;
  private boolean down;
  private boolean left;
  private boolean right;

  private boolean sUp;
  private boolean sDown;
  private boolean sLeft;
  private boolean sRight;


  public GameController(ControllableModel model) {
    this.model = model;
    float intervalSeconds = 1f / TICKS_PER_SECOND;
    this.gameLoop = new GameLoop(model, intervalSeconds);
    gameLoop.start();

  }

  
  @Override
  public boolean keyDown(int keycode) {
    return handleKeyPress(keycode, true);
  }
  
  @Override
  public boolean keyUp(int keycode) {
    return handleKeyPress(keycode, false);
  }

  private boolean handleKeyPress(int keycode, boolean keyDown) {

    switch (keycode) {
      case Input.Keys.S,
      Input.Keys.W,
      Input.Keys.A,
      Input.Keys.D -> handleMovementKeys(keycode, keyDown);
      case Input.Keys.UP,
      Input.Keys.DOWN,
      Input.Keys.LEFT,
      Input.Keys.RIGHT-> handleAttackKeys(keycode, keyDown);
      case Input.Keys.BACKSPACE -> {if (keyDown) {model.toggleDebug();}}
      case Input.Keys.SHIFT_LEFT -> model.isSprinting(keyDown);
      //case Input.Keys.SPACE -> {if (keyDown) model.fireWeapon(Gdx.input.getX(), Gdx.input.getY(), camera);}
      case Input.Keys.SPACE -> model.setAttacking(keyDown);
      case Input.Keys.P -> {if (keyDown) {model.pauseGame();}}
      case Input.Keys.O -> {if (keyDown) {model.gameOver();}}
      case Input.Keys.ENTER -> {if (keyDown && GameModel.state == GameState.GAME_OVER ) {model.restartGame();}}
      case Input.Keys.R -> {if (keyDown) {model.restartGame();}}

      default -> {
        return false;
      }
    }
    return true;
  }

  private void handleAttackKeys(int keycode, boolean keyDown) {
    switch (keycode) {
      case Input.Keys.DOWN -> sDown = keyDown;
      case Input.Keys.UP -> sUp = keyDown;
      case Input.Keys.LEFT -> sLeft = keyDown;
      case Input.Keys.RIGHT -> sRight = keyDown;
    }

    int x = (sRight ? 1:0) - (sLeft ? 1:0);
    int y = (sUp ? 1:0) - (sDown ? 1:0);
    model.attackDirection(x, y);
  }

  private void handleMovementKeys(int keycode, boolean keyDown) {
    switch (keycode) {
      case Input.Keys.S -> down = keyDown;
      case Input.Keys.W -> up = keyDown;
      case Input.Keys.A -> left = keyDown;
      case Input.Keys.D -> right = keyDown;
    }

    int x = (right ? 1:0) - (left ? 1:0);
    int y = (up ? 1:0) - (down ? 1:0);
    model.moveDirection(x, y);
  }

  @Override
  public void dispose() {
    gameLoop.dispose();
  }
}
