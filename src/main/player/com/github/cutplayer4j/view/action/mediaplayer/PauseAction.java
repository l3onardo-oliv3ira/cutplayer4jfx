package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.view.action.Resource;

@SuppressWarnings("serial")
final class PauseAction extends MediaPlayerAction {

  PauseAction(Resource resource) {
    super(resource);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    application().mediaPlayer().pause();
  }
}
