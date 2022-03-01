package com.github.cutplayer4j;

import java.awt.image.BufferedImage;

import com.github.cutplayer4j.gui.IMediaPlayerEventListener;

public interface IMediaPlayer {

  void mute();

  void pause();

  void play(String uri);

  void play();

  void setRate(float rate);

  void skipTime(long delta);

  void stop();

  boolean isPlaying();
  
  double volume();

  void setVolume(double volume);
  
  BufferedImage snapshots();

  void setPosition(long position);

  long position();
  
  void toggleFullScreen();

  void attachListener(IMediaPlayerEventListener listener);

  long duration();
}

