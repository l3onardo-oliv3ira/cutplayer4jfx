
package com.github.cutplayer4j.event;

import java.io.File;
import java.net.URI;

public class PlayingEvent {
  private final File file;
  
  public PlayingEvent(URI source) {
    this.file = new File(source);
  }
  
  public File getFile() {
    return file;
  }
}
