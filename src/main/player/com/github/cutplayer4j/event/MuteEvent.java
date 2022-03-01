package com.github.cutplayer4j.event;

public final class MuteEvent {
  private final boolean mute;
  
  public MuteEvent(boolean mute) {
    this.mute = mute;
  }
  
  public final boolean isMute() {
    return this.mute;
  }
}
