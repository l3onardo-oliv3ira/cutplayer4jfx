package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.view.action.Resource.resource;

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

  public MediaPlayerActions() {
    playbackSpeedActions   = createPlaybackSpeedActions();
    playbackSkipActions    = createPlaybackSkipActions();
    playbackControlActions = createPlaybackControlActions();
    audioControlActions    = createAudioControlActions();
    playbackPlayAction     = new PlayAction(resource("menu.playback.item.play"));
    playbackStopAction     = new StopAction(resource("menu.playback.item.stop"));
    videoSnapshotAction    = new SnapshotAction(resource("menu.video.item.snapshot"));
  }

  private List<Action> createPlaybackSpeedActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(new RateAction(4f, resource("menu.playback.item.speed.item.x4")));
    actions.add(new RateAction(2f, resource("menu.playback.item.speed.item.x2")));
    actions.add(new RateAction(1f, resource("menu.playback.item.speed.item.normal")));
    actions.add(new RateAction(0.5f, resource("menu.playback.item.speed.item./2")));
    actions.add(new RateAction(0.25f, resource("menu.playback.item.speed.item./4")));
    return ImmutableList.copyOf(actions);
  }

  private List<Action> createPlaybackSkipActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(new SkipAction(10000, resource("menu.playback.item.skipForward" )));
    actions.add(new SkipAction(-10000, resource("menu.playback.item.skipBackward")));
    return ImmutableList.copyOf(actions);
  }

  private List<Action> createPlaybackControlActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(new PlayAction(resource("menu.playback.item.play")));
    actions.add(new StopAction(resource("menu.playback.item.stop")));
    return ImmutableList.copyOf(actions);
  }

  private List<Action> createAudioControlActions() {
    List<Action> actions = new ArrayList<>();
    actions.add(new VolumeAction(10,  resource("menu.audio.item.increaseVolume")));
    actions.add(new VolumeAction(-10, resource("menu.audio.item.decreaseVolume")));
    actions.add(new MuteAction(resource("menu.audio.item.mute")));
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
}
