
package com.github.cutplayer4j.event;

public abstract class MarkEvent implements IMarkEvent {

  private long time;
  
  public MarkEvent(long time) {
    this.time = time;
  }
  
  @Override
  public final long getTime(){
    return time;
  }
}
