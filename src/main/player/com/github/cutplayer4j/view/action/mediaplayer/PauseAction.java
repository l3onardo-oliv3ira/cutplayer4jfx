package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.utils4j.gui.IResourceAction;

final class PauseAction extends MediaPlayerAction {

  PauseAction(IResourceAction resource) {
    super(resource);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    IMediaPlayer component = application().mediaPlayer();
    if (component.isPlaying()) {
      component.pause();
    }
  }
}
