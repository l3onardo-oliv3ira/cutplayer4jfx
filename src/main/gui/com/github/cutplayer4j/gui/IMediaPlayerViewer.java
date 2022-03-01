package com.github.cutplayer4j.gui;

import com.github.cutplayer4j.IMediaPlayer;

public interface IMediaPlayerViewer {
  IMediaPlayer mediaPlayer();

  void showIdle();

  void showVideo();
}
