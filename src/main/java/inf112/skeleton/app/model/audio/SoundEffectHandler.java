package inf112.skeleton.app.model.audio;

import inf112.skeleton.app.event.Event;
import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;

public class SoundEffectHandler {

  public SoundEffectHandler() {
    IEventBus eventBus = GlobalEventBus.getInstance(2);
    eventBus.on("SoundEffect", this::playAudio);
  }

  private void playAudio(Event event) {
    if (event instanceof SoundEffectEvent soundEffectEvent) {
      long id = soundEffectEvent.getSound().play();
      if (soundEffectEvent.getAudioDef() == "explode" || soundEffectEvent.getAudioDef() == "punch"){
        soundEffectEvent.getSound().setVolume(id, 0.3f);
      }
    }
  }
}