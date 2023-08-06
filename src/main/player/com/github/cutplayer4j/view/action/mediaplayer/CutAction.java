
package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.CutEndEvent;
import com.github.cutplayer4j.event.CutStartEvent;
import com.github.utils4j.gui.IResourceAction;
import com.github.utils4j.gui.imp.StandardAction;

final class CutAction extends StandardAction {

  private boolean start;
  
  CutAction(boolean markStart,  IResourceAction resource) {
    super(resource);
    this.start = markStart;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    IMediaPlayer player = application().mediaPlayer();
    if (player.isMarkable()) {
      long currentTime = player.position();
      if (start) {
        application().post(new CutStartEvent(currentTime));
      } else {
        application().post(new CutEndEvent(currentTime));
      }
    }
  }
}
