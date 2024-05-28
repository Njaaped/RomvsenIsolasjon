package inf112.skeleton.app.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class MediaTest {

  @BeforeEach
  void setUpBeforeEach() {
    HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    ApplicationListener listener = new ApplicationListener() {

      @Override
      public void create() {
        // TODO Auto-generated method stub

      }

      @Override
      public void resize(int width, int height) {
        // TODO Auto-generated method stub

      }

      @Override
      public void render() {
        // TODO Auto-generated method stub

      }

      @Override
      public void pause() {
        // TODO Auto-generated method stub

      }

      @Override
      public void resume() {
        // TODO Auto-generated method stub

      }

      @Override
      public void dispose() {
        // TODO Auto-generated method stub

      }};
    new HeadlessApplication(listener, config);
    Gdx.gl = mock(GL20.class);
    Gdx.gl20 = mock(GL20.class);
  }


  @Test
  public void testloading() {
    Media.load_assets();
    assertNotNull(Media.punch1);
  }

  @Test
  public void testAllLoadedSuccessfully() {
    Media.load_assets();

    assertNotNull(Media.player);
    assertNotNull(Media.ground);
    assertNotNull(Media.space1);
    assertNotNull(Media.space2);
    assertNotNull(Media.space3);
    assertNotNull(Media.space4);
    assertNotNull(Media.space5);
    assertNotNull(Media.wallTop);
    assertNotNull(Media.wallTop2);
    assertNotNull(Media.wallTop3);
    assertNotNull(Media.wallBottom);
    assertNotNull(Media.wallLeft);
    assertNotNull(Media.wallRight);
    assertNotNull(Media.wallTopLeftInside);
    assertNotNull(Media.wallTopRightInside);
    assertNotNull(Media.wallBottomLeftInside);
    assertNotNull(Media.wallBottomRightInside);
    assertNotNull(Media.wallTopLeftOutside);
    assertNotNull(Media.wallTopRightOutside);
    assertNotNull(Media.wallBottomLeftOutside);
    assertNotNull(Media.wallBottomRightOutside);
    assertNotNull(Media.wallVertical);
    assertNotNull(Media.alien);
    assertNotNull(Media.alienTwo);
    assertNotNull(Media.bullet);
    assertNotNull(Media.black);
    assertNotNull(Media.red);
    assertNotNull(Media.gray);
    assertNotNull(Media.green);
    assertNotNull(Media.brightGreen);
    assertNotNull(Media.redPlayer);
    assertNotNull(Media.orangePlayer);
    assertNotNull(Media.yellowPlayer);
    assertNotNull(Media.greenPlayer);
    assertNotNull(Media.bluePlayer);
    assertNotNull(Media.indigoPlayer);
    assertNotNull(Media.purplePlayer);
    assertNotNull(Media.star);
    assertNotNull(Media.healthPacket);
    assertNotNull(Media.raygun);
    assertNotNull(Media.alienBomber1);
    assertNotNull(Media.alienBomber2);
    assertNotNull(Media.alienBomber3);
    assertNotNull(Media.alienBomber4);
    assertNotNull(Media.explosion);
    assertNotNull(Media.redDot);
    assertNotNull(Media.greenDot);
    assertNotNull(Media.laser1);
    assertNotNull(Media.laser2);
    assertNotNull(Media.laser3);
    assertNotNull(Media.laser4);
    assertNotNull(Media.laser5);
    assertNotNull(Media.laser6);
    assertNotNull(Media.laser7);
    assertNotNull(Media.laser8);
    assertNotNull(Media.laser9);
    assertNotNull(Media.punch1);
    assertNotNull(Media.punch2);
    assertNotNull(Media.punch3);
    assertNotNull(Media.punch4);
    assertNotNull(Media.explode);
    assertNotNull(Media.loadingMusic);
    assertNotNull(Media.battleMusic);
    assertNotNull(Media.getTexture("startBackground"));
    assertNotNull(Media.getTexture("buttonOffStart"));
    assertNotNull(Media.getTexture("buttonOnStart"));
    assertNotNull(Media.getTexture("buttonOffExit"));
    assertNotNull(Media.getTexture("buttonOnExit"));
  }

  @Test
  public void testGetters() {
    Media.load_assets();


    Sound[] punch = Media.getPunchSounds();
    assertNotEquals(0, punch.length);
    for (Sound element : punch) {
      assertNotNull(element);
    }

    Sound[] laser = Media.getLaserSounds();
    assertNotEquals(0, laser.length);
    for (Sound element : laser) {
      assertNotNull(element);
    }

    Texture[] colors = Media.getGodModeColors();
    assertNotEquals(0, colors.length);
    for (Texture element : colors) {
      assertNotNull(element);
    }

    Texture[] bombers = Media.getAlienBomber();
    assertNotEquals(0, bombers.length);
    for (Texture element : bombers) {
      assertNotNull(element);
    }


    assertNotNull(Media.getTexture("player"));
    assertNotNull(Media.getTexture("ground"));
    assertNotNull(Media.getTexture("space1"));
    assertNotNull(Media.getTexture("space2"));
    assertNotNull(Media.getTexture("space3"));
    assertNotNull(Media.getTexture("space4"));
    assertNotNull(Media.getTexture("space5"));
    assertNotNull(Media.getTexture("wallTop"));
    assertNotNull(Media.getTexture("wallTop2"));
    assertNotNull(Media.getTexture("wallTop3"));
    assertNotNull(Media.getTexture("wallBottom"));
    assertNotNull(Media.getTexture("wallLeft"));
    assertNotNull(Media.getTexture("wallRight"));
    assertNotNull(Media.getTexture("wallTopLeftInside"));
    assertNotNull(Media.getTexture("wallTopRightInside"));
    assertNotNull(Media.getTexture("wallBottomLeftInside"));
    assertNotNull(Media.getTexture("wallBottomRightInside"));
    assertNotNull(Media.getTexture("wallTopLeftOutside"));
    assertNotNull(Media.getTexture("wallTopRightOutside"));
    assertNotNull(Media.getTexture("wallBottomLeftOutside"));
    assertNotNull(Media.getTexture("wallBottomRightOutside"));
    assertNotNull(Media.getTexture("wallVertical"));
    assertNotNull(Media.getTexture("alien"));
    assertNotNull(Media.getTexture("alienTwo"));
    assertNotNull(Media.getTexture("bullet"));
    assertNotNull(Media.getTexture("black"));
    assertNotNull(Media.getTexture("red"));
    assertNotNull(Media.getTexture("gray"));
    assertNotNull(Media.getTexture("green"));
    assertNotNull(Media.getTexture("brightGreen"));
    assertNotNull(Media.getTexture("startBackground"));
    assertNotNull(Media.getTexture("buttonOffStart"));
    assertNotNull(Media.getTexture("buttonOnStart"));
    assertNotNull(Media.getTexture("buttonOffExit"));
    assertNotNull(Media.getTexture("buttonOnExit"));
  }

}
