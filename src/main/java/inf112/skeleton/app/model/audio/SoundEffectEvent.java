package inf112.skeleton.app.model.audio;

import com.badlogic.gdx.audio.Sound;
import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.view.Media;

public class SoundEffectEvent extends Event {
  private Sound sound;
  private String audioDef;

  public SoundEffectEvent(Object caller, String audioDef) {
    super(caller);
    this.audioDef = audioDef;
    if (audioDef == null) {
      throw new IllegalArgumentException("Audio definition cannot be null");
    }
    if (audioDef.isEmpty()) {
      throw new IllegalArgumentException("Audio definition cannot be empty");
    }
    if (audioDef.equals("laser")) {
      sound = Media.getLaserSounds()[((int) (Math.random() * Media.getLaserSounds().length))];
    }
    if (audioDef.equals("explode")) {
      sound = Media.explode;
    }
    if (audioDef.equals("punch")) {
      sound = Media.getPunchSounds()[((int) (Math.random() * Media.getPunchSounds().length))];
    }
  }

  public Sound getSound() {
    return sound;
  }

  public String getAudioDef() {
    return audioDef;
  }
}
