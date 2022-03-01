package com.github.cutplayer4j.gui;

public interface IMediaPlayerEventListener {

  void ready();

  void error();

  void finished();

  void stopped();

  void paused();

  void playing();
}
