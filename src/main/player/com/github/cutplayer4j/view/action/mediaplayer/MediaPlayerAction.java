
package com.github.cutplayer4j.view.action.mediaplayer;

import com.github.utils4j.gui.IResourceAction;
import com.github.utils4j.gui.imp.StandardAction;

abstract class MediaPlayerAction extends StandardAction {

  private static final long serialVersionUID = 1L;

  MediaPlayerAction(IResourceAction resource) {
    super(resource);
  }
}
