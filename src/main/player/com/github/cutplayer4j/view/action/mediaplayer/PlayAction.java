/*
* MIT License
* 
* Copyright (c) 2022 Leonardo de Lima Oliveira
* 
* https://github.com/l3onardo-oliv3ira
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/


package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.cutplayer4j.view.action.ResourceAction.resource;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.PausedEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.event.StoppedEvent;
import com.github.utils4j.gui.IResourceAction;
import com.google.common.eventbus.Subscribe;

final class PlayAction extends MediaPlayerAction {

  PlayAction(IResourceAction resource) {
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
    IResourceAction resource = resource("menu.playback.item.pause");
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
    IResourceAction resource = resource("menu.playback.item.play");
    putValue(NAME, resource.name());
    putValue(SMALL_ICON, resource.menuIcon());
    putValue(LARGE_ICON_KEY, resource.buttonIcon());
  }
}
