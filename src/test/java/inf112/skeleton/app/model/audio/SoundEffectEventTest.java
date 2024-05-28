package inf112.skeleton.app.model.audio;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.skeleton.app.view.Media;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;



public class SoundEffectEventTest {
  static HeadlessApplication headlessApplication;

  public static void initApplication() {
    if (headlessApplication == null) {
      HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
      ApplicationListener listener = new ApplicationListener() {
        @Override
        public void create() {
        }

        @Override
        public void resize(int width, int height) {
        }

        @Override
        public void render() {
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void dispose() {
        }
      };
      headlessApplication = new HeadlessApplication(listener, config);
      Gdx.graphics.setForegroundFPS(60);
      assertNotNull(Gdx.files);
    }
  }

  @Test
  public void TestExeptions() {
    assertThrows(IllegalArgumentException.class, new Executable() {
      @Override
      public void execute() throws Throwable {
        new SoundEffectEvent(this, null);
      }
    }
    );

    assertThrows(IllegalArgumentException.class, new Executable() {
              @Override
              public void execute() throws Throwable {
                new SoundEffectEvent(this, "");
              }
            }
    );
  }

  @Test
  public void returnsCorrectSoundEffect() {
    initApplication();
    Gdx.gl = Mockito.mock(GL20.class);
    Media.load_assets();


    SoundEffectEvent se = new SoundEffectEvent(this, "laser");
    Sound s = se.getSound();
    List ll = List.of(new Sound[]{Media.laser1, Media.laser2, Media.laser3, Media.laser4, Media.laser5, Media.laser6, Media.laser7, Media.laser8, Media.laser9});
    assertTrue(ll.contains(s));

    se = new SoundEffectEvent(this, "explode");
    s = se.getSound();
    assertEquals(s, Media.explode);

    se = new SoundEffectEvent(this, "punch");
    s = se.getSound();
    List pl = List.of(new Sound[]{Media.punch1, Media.punch2, Media.punch3, Media.punch4});
    assertTrue(pl.contains(s));
  }
}
