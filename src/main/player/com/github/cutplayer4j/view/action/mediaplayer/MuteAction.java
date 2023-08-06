
package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.event.MuteEvent;
import com.github.utils4j.gui.IResourceAction;

final class MuteAction extends MediaPlayerAction {

  MuteAction(IResourceAction resource) {
    super(resource);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    application().post(new MuteEvent(application().mediaPlayer().toggleMute()));
  }
}
