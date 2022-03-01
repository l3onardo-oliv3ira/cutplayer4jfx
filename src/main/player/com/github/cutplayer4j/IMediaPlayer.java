package com.github.cutplayer4j;

import java.awt.image.BufferedImage;

public interface IMediaPlayer {

  void mute();

  void pause();

  void play(String uri);

  void play();

  void setRate(float rate);

  void skipTime(long delta);

  void stop();

  boolean isPlaying();
  
  int volume();

  void setVolume(int volume);
  
  BufferedImage snapshots();

  void setPosition(float f);

  float position();

  void toggleFullScreen();

}

