
package com.github.cutplayer4j.event;

public final class VolumeEvent {
  private final double volume;
  
  public VolumeEvent(double volume) {
    this.volume = volume;
  }
  
  public final double volume() {
    return this.volume;
  }
}
