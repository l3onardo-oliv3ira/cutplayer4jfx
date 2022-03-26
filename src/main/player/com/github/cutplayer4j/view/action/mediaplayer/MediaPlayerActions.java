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

import static com.github.cutplayer4j.view.action.ResourceAction.resource;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import com.google.common.collect.ImmutableList;

public final class MediaPlayerActions {

  private final List<Action> playbackSpeedActions;
  private final List<Action> playbackSkipActions;
  private final List<Action> playbackControlActions;
  private final List<Action> audioControlActions;

  private final Action playbackPlayAction;
  private final Action playbackStopAction;
  private final Action videoSnapshotAction;
  private final Action skipForwardAction;
  private final Action skipBackAction;
  private final Action muteAction;
  private final Action cutStartAction;
  private final Action cutEndAction;

  public MediaPlayerActions() {
    playbackPlayAction     = new PlayAction(resource("menu.playback.item.play"));
    playbackStopAction     = new StopAction(resource("menu.playback.item.stop"));
    videoSnapshotAction    = new SnapshotAction(resource("menu.video.item.snapshot"));
    skipForwardAction      = new SkipAction(60, resource("menu.playback.item.skipForward" ));
    skipBackAction         = new SkipAction(-60, resource("menu.playback.item.skipBackward"));
    cutStartAction         = new CutAction(true, resource("menu.playback.item.cutStart"));
    cutEndAction           = new CutAction(false, resource("menu.playback.item.cutEnd"));
    
    muteAction             = new MuteAction(resource("menu.audio.item.mute"));
    playbackSpeedActions   = createPlaybackSpeedActions();
    playbackControlActions = createPlaybackControlActions();
    audioControlActions    = createAudioControlActions();
    playbackSkipActions    = createPlaybackSkipActions();
  }

  private List<Action> createPlaybackSpeedActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(new RateAction(2f, resource("menu.playback.item.speed.item.2x")));
    actions.add(new RateAction(1.5f, resource("menu.playback.item.speed.item.1.5x")));
    actions.add(new RateAction(1f, resource("menu.playback.item.speed.item.normal")));
    actions.add(new RateAction(0.5f, resource("menu.playback.item.speed.item.0.5x")));
    actions.add(new RateAction(0.25f, resource("menu.playback.item.speed.item.0.25")));
    return ImmutableList.copyOf(actions);
  }

  private List<Action> createPlaybackSkipActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(skipForwardAction);
    actions.add(skipBackAction);
    return ImmutableList.copyOf(actions);
  }

  private List<Action> createPlaybackControlActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(playbackPlayAction);
    actions.add(playbackStopAction);
    return ImmutableList.copyOf(actions);
  }

  private List<Action> createAudioControlActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(new VolumeAction(1,  resource("menu.audio.item.increaseVolume")));
    actions.add(new VolumeAction(-1, resource("menu.audio.item.decreaseVolume")));
    actions.add(muteAction);
    return ImmutableList.copyOf(actions);
  }

  public List<Action> playbackSpeedActions() {
    return playbackSpeedActions;
  }
  
  public List<Action> playbackSkipActions() {
    return playbackSkipActions;
  }

  public List<Action> playbackControlActions() {
    return playbackControlActions;
  }

  public List<Action> audioControlActions() {
    return audioControlActions;
  }
  
  public Action playbackPlayAction() {
    return playbackPlayAction;
  }

  public Action playbackStopAction() {
    return playbackStopAction;
  }

  public Action videoSnapshotAction() {
    return videoSnapshotAction;
  }

  public Action skipBackAction() {
    return skipBackAction;
  }

  public Action skipForwardAction() {
    return skipForwardAction;
  }

  public Action muteAction() {
    return muteAction;
  }

  public Action cutStartAction() {
    return cutStartAction;
  }

  public Action cutEndAction() {
    return cutEndAction;
  }
}
