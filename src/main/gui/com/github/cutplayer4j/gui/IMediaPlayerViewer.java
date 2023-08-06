
package com.github.cutplayer4j.gui;

import javax.swing.JPanel;

import com.github.cutplayer4j.IMediaPlayer;

public interface IMediaPlayerViewer {
  IMediaPlayer mediaPlayer();

  void showIdle();

  void showVideo();
  
  void close();
  
  JPanel asPanel();
}
