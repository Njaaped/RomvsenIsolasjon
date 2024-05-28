package inf112.skeleton.app.model.audio;

import com.badlogic.gdx.audio.Music;
import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;

public class MusicHandler {
  Music currentMusic;
  public MusicHandler() {
    IEventBus eventBus = GlobalEventBus.getInstance(3);
    eventBus.on("PlayMusic", this::playMusic);
    eventBus.on("StopMusic", this::stopMusic);
  }

  private void playMusic(Event event) {
    if (currentMusic != null) {
      currentMusic.stop();
      currentMusic.dispose();
    }
    if (event instanceof MusicEvent musicEvent) {
      currentMusic = musicEvent.getMusic();
      currentMusic.setLooping(true);
      currentMusic.setVolume(0.2f);
      currentMusic.play();
    }
  }

  private void stopMusic(Event event) {
    if (currentMusic != null) {
      currentMusic.stop();
      currentMusic.dispose();
    }
  }
}
