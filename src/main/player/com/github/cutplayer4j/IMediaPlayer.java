package com.github.cutplayer4j;

import java.awt.image.BufferedImage;

public interface IMediaPlayer {

  boolean toggleMute();
  
  void pause();

  boolean play(String uri);

  void play();

  void setRate(double rate);

  void skipTime(long delta);

  void stop();
  
  void close();

  boolean isPlaying();
  
  double volume();

  void setVolume(double volume);
  
  BufferedImage snapshots();

  void setPosition(long position);

  long position();
  
  long duration();

  boolean isMarkable();
}

