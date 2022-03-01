package com.github.cutplayer4j.view.action.mediaplayer;

import com.github.cutplayer4j.view.action.Resource;
import com.github.cutplayer4j.view.action.StandardAction;

abstract class MediaPlayerAction extends StandardAction {

  private static final long serialVersionUID = 1L;

  MediaPlayerAction(Resource resource) {
    super(resource);
  }
}
