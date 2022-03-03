package com.github.cutplayer4j.event;

import com.github.videohandler4j.IVideoSlice;

public class SelectVideoSliceEvent {
  private IVideoSlice slice;

  public SelectVideoSliceEvent(IVideoSlice slice) {
    super();
    this.slice = slice;
  }

  public final IVideoSlice getSlice() {
    return slice;
  }
  
}
