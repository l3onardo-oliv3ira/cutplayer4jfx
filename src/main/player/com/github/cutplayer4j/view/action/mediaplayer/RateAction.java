package com.github.cutplayer4j.view.action.mediaplayer;

import java.awt.event.ActionEvent;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.view.action.Resource;

@SuppressWarnings("serial")
final class RateAction extends MediaPlayerAction {

  private final float rate;

  RateAction(float rate, Resource resource) {
    super(resource);
    this.rate = rate;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    application().mediaPlayer().setRate(rate);
  }
}
