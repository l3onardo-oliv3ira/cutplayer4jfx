package com.github.cutplayer4j.event;

import java.io.File;

public class OpenRecentEvent {
  private File file;
  
  public OpenRecentEvent(File file) {
    this.file = file;
  }

  public final File getFile() {
    return file;
  }
}
