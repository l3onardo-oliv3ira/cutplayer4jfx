package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.utils4j.gui.IResourceAction;

final class SkipAction extends MediaPlayerAction {

  private final long delta;

  SkipAction(long delta, IResourceAction resource) {
    super(resource);
    this.delta = delta;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    application().mediaPlayer().skipTime(delta);
  }

}
