
package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import com.github.cutplayer4j.event.SnapshotImageEvent;
import com.github.utils4j.gui.IResourceAction;

final class SnapshotAction extends MediaPlayerAction {

  SnapshotAction(IResourceAction resource) {
    super(resource);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    BufferedImage image = application().mediaPlayer().snapshots();
    if (image != null) {
      application().post(new SnapshotImageEvent(image));
    }
  }
}
