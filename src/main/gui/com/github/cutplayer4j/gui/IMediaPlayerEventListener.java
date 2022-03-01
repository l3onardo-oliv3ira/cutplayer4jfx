package com.github.cutplayer4j.gui;

public interface IMediaPlayerEventListener {

  void mediaPlayerReady();

  void timeChanged(long newTime);

  void lengthChanged(long newLength);

  void error();

  void finished();

  void stopped();

  void paused();

  void playing();
}
