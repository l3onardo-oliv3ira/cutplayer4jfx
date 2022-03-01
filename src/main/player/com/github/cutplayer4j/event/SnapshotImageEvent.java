package com.github.cutplayer4j.event;

import java.awt.image.BufferedImage;

public final class SnapshotImageEvent {

  private final BufferedImage image;

  public SnapshotImageEvent(BufferedImage image) {
    this.image = image;
  }

  public BufferedImage image() {
    return image;
  }
}
