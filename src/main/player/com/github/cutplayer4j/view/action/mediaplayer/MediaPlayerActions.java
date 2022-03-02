package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.view.action.Resource.resource;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import com.github.cutplayer4j.event.RateEvent;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;

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

  public MediaPlayerActions() {
    playbackPlayAction     = new PlayAction(resource("menu.playback.item.play"));
    playbackStopAction     = new StopAction(resource("menu.playback.item.stop"));
    videoSnapshotAction    = new SnapshotAction(resource("menu.video.item.snapshot"));
    skipForwardAction      = new SkipAction(60, resource("menu.playback.item.skipForward" ));
    skipBackAction         = new SkipAction(-60, resource("menu.playback.item.skipBackward"));
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
}
