package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.cutplayer4j.view.action.Resource.resource;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.PausedEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.event.StoppedEvent;
import com.github.cutplayer4j.view.action.Resource;
import com.google.common.eventbus.Subscribe;

@SuppressWarnings("serial")
final class PlayAction extends MediaPlayerAction {

  PlayAction(Resource resource) {
    super(resource);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    IMediaPlayer component = application().mediaPlayer();
    if (!component.isPlaying()) {
      component.play();
    } else {
      component.pause();
    }
  }
  
  @Subscribe
  public void onPlay(PlayingEvent e) {
    Resource resource = resource("menu.playback.item.pause");
    putValue(NAME, resource.name());
    putValue(SMALL_ICON, resource.menuIcon());
    putValue(LARGE_ICON_KEY, resource.buttonIcon());
  }
  
  @Subscribe
  public void onPause(PausedEvent e) {
    onStop(StoppedEvent.INSTANCE);
  }
  
  @Subscribe
  public void onStop(StoppedEvent e) {
    Resource resource = resource("menu.playback.item.play");
    putValue(NAME, resource.name());
    putValue(SMALL_ICON, resource.menuIcon());
    putValue(LARGE_ICON_KEY, resource.buttonIcon());
  }
}
