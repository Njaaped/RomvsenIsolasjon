package inf112.skeleton.app.model.audio;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MusicEventTest {
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
                new MusicEvent(this, null);
              }
            }
    );

    assertThrows(IllegalArgumentException.class, new Executable() {
              @Override
              public void execute() throws Throwable {
                new MusicEvent(this, "");
              }
            }
    );
  }

  @Test
  public void returnsCorrectMusic() {
    initApplication();
    Gdx.gl = Mockito.mock(GL20.class);
    Media.load_assets();


    MusicEvent me = new MusicEvent(this, "loading");
    Music m = me.getMusic();
    assertEquals(m, Media.loadingMusic);


    me = new MusicEvent(this, "battle");
    m = me.getMusic();
    assertEquals(m, Media.battleMusic);
  }
}
