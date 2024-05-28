package inf112.skeleton.app.model.audio;

import com.badlogic.gdx.audio.Music;
import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.view.Media;

public class MusicEvent extends Event {
  Music music;

  public MusicEvent(Object caller, String audioDef) {
    super(caller);
    if (audioDef == null) {
      throw new IllegalArgumentException("Audio definition cannot be null");
    }
    if (audioDef.isEmpty()) {
      throw new IllegalArgumentException("Audio definition cannot be empty");
    }
    if (audioDef.equals("loading")) {
      music = Media.loadingMusic;
    }
    if (audioDef.equals("battle")) {
      music = Media.battleMusic;
    }
  }

  public Music getMusic() {
    return music;
  }
}
