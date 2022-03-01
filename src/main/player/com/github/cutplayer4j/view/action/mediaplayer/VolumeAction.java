package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.view.action.Resource;

@SuppressWarnings("serial")
final class VolumeAction extends MediaPlayerAction {

  private final int delta;

  VolumeAction(int delta, Resource resource) {
    super(resource);
    this.delta = delta;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    IMediaPlayer component = application().mediaPlayer();
    component.setVolume(component.volume() + delta);
  }
}
