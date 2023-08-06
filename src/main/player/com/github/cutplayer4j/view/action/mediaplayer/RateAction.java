
package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.event.RateEvent;
import com.github.utils4j.gui.IResourceAction;
import com.google.common.eventbus.Subscribe;


final class RateAction extends MediaPlayerAction {

  private final double rate;

  RateAction(double rate, IResourceAction resource) {
    super(resource);
    this.rate = rate;
    select(this.rate == 1);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    application().mediaPlayer().setRate(rate);
    application().post(new RateEvent(rate));
  }
  
  @Subscribe
  public void onRate(RateEvent event) {
    super.select(this.rate == event.getRate());
  }
}
