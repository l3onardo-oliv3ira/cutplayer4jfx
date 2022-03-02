package com.github.cutplayer4j.gui.imp;

import com.github.utils4j.gui.IPicture;

public enum Images implements IPicture {
  
  CUTPLAYER_BLACK("/cutplayer-black.png"),

  CUTPLAYER("/cutplayer.png");

  final String path;
  
  Images(String path) {
    this.path = path;
  }

  @Override
  public String path() {
    return path;
  }
}


