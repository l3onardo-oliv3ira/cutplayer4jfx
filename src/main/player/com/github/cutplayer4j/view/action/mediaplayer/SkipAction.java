package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.utils4j.gui.IResourceAction;

final class SkipAction extends MediaPlayerAction {

  private final long deltaSeconds;

  SkipAction(long deltaSeconds, IResourceAction resource) {
    super(resource);
    this.deltaSeconds = deltaSeconds;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    application().mediaPlayer().skipTime(deltaSeconds);
  }
}
