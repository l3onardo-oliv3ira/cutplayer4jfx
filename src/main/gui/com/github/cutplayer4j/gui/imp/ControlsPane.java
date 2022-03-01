package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.cutplayer4j.event.PausedEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.event.StoppedEvent;
import com.github.cutplayer4j.view.action.mediaplayer.MediaPlayerActions;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

final class ControlsPane extends BasePanel {

  private final Icon playIcon = newIcon("play");

  private final Icon pauseIcon = newIcon("pause");

  private final Icon previousIcon = newIcon("previous");

  private final Icon nextIcon = newIcon("next");

  private final Icon fullscreenIcon = newIcon("fullscreen");

  private final Icon extendedIcon = newIcon("extended");

  private final Icon snapshotIcon = newIcon("snapshot");

  private final Icon cutStartIcon = newIcon("cut-start");
  
  private final Icon cutEndIcon = newIcon("cut-end");
  
  private final Icon volumeHighIcon = newIcon("volume-high");

  private final Icon volumeMutedIcon = newIcon("volume-muted");

  private final JButton playPauseButton;

  private final JButton previousButton;

  private final JButton stopButton;

  private final JButton nextButton;

  private final JButton fullscreenButton;

  private final JButton extendedButton;

  private final JButton snapshotButton;
  
  private final JButton cutStartButton;

  private final JButton cutEndButton;

  private final JButton muteButton;

  private final JSlider volumeSlider;

  ControlsPane(MediaPlayerActions mediaPlayerActions) {
    playPauseButton = new BigButton();
    playPauseButton.setAction(mediaPlayerActions.playbackPlayAction());
    previousButton = new StandardButton();
    previousButton.setIcon(previousIcon);
    stopButton = new StandardButton();
    stopButton.setAction(mediaPlayerActions.playbackStopAction());
    nextButton = new StandardButton();
    nextButton.setIcon(nextIcon);
    fullscreenButton = new StandardButton();
    fullscreenButton.setIcon(fullscreenIcon);
    extendedButton = new StandardButton();
    extendedButton.setIcon(extendedIcon);
    snapshotButton = new StandardButton();
    snapshotButton.setAction(mediaPlayerActions.videoSnapshotAction());
    
    cutStartButton = new StandardButton();
    cutStartButton.setIcon(cutStartIcon);
    //cutStartButton.setAction(mediaPlayerActions.videoSnapshotAction());
    
    cutEndButton = new StandardButton();
    cutEndButton.setIcon(cutEndIcon);
    //cutEndButton.setAction(mediaPlayerActions.videoSnapshotAction());
    
    muteButton = new StandardButton();
    muteButton.setIcon(volumeHighIcon);
    volumeSlider = new JSlider();
    volumeSlider.setMinimum(0);
    volumeSlider.setMaximum(100);

    setLayout(new MigLayout("fill, insets 0 0 0 0", "[]12[]0[]0[]12[]0[]12[]12[]0[]push[]0[]", "[]"));

    add(playPauseButton);
    add(previousButton, "sg 1");
    add(stopButton, "sg 1");
    add(nextButton, "sg 1");

    add(fullscreenButton, "sg 1");
    add(extendedButton, "sg 1");

    add(snapshotButton, "sg 1");

    add(cutStartButton, "sg 1");
    add(cutEndButton, "sg 1");
    
    add(muteButton, "sg 1");
    add(volumeSlider, "wmax 100");

    volumeSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        application().mediaPlayer().setVolume(volumeSlider.getValue());
      }
    });

    // FIXME really these should share common actions

    muteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        application().mediaPlayer().mute();
      }
    });

    fullscreenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        application().mediaPlayer().toggleFullScreen();
      }
    });

    extendedButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
       // application().post(ShowEffectsEvent.INSTANCE);
      }
    });
  }

  @Subscribe
  public void onPlaying(PlayingEvent event) {
    playPauseButton.setIcon(pauseIcon); // FIXME best way to do this? should be via the action really?
  }

  @Subscribe
  public void onPaused(PausedEvent event) {
    playPauseButton.setIcon(playIcon); // FIXME best way to do this? should be via the action really?
  }

  @Subscribe
  public void onStopped(StoppedEvent event) {
    playPauseButton.setIcon(playIcon); // FIXME best way to do this? should be via the action really?
  }
}
