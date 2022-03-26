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


package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.cutplayer4j.event.MuteEvent;
import com.github.cutplayer4j.event.PausedEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.event.SelectVideoSliceEvent;
import com.github.cutplayer4j.event.StoppedEvent;
import com.github.cutplayer4j.event.TickEvent;
import com.github.cutplayer4j.event.VolumeEvent;
import com.github.cutplayer4j.view.action.mediaplayer.MediaPlayerActions;
import com.github.videohandler4j.IVideoSlice;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

final class ControlsPane extends EventAwarePanel {

  private final Icon playIcon = newIcon("play");

  private final Icon pauseIcon = newIcon("pause");

  private final Icon skipBackIcon = newIcon("skip-back");

  private final Icon skipForwardIcon = newIcon("skip-forward");

  private final Icon fullscreenIcon = newIcon("fullscreen");

  private final Icon volumeHighIcon = newIcon("volume-high");

  private final Icon volumeMutedIcon = newIcon("volume-muted");
  
  private final JButton playPauseButton;

  private final JButton skipBackButton;

  private final JButton stopButton;

  private final JButton skipForwardButton;

  private final JButton fullscreenButton;

  private final JButton snapshotButton;
  
  private final JButton cutStartButton;

  private final JButton cutEndButton;

  private final JButton muteButton;

  private final JSlider volumeSlider;

  ControlsPane(MediaPlayerActions mediaPlayerActions) {
    playPauseButton = new BigButton();
    playPauseButton.setAction(mediaPlayerActions.playbackPlayAction());
    
    skipBackButton = new StandardButton();
    skipBackButton.setIcon(skipBackIcon);
    skipBackButton.setAction(mediaPlayerActions.skipBackAction());
    
    skipForwardButton = new StandardButton();
    skipForwardButton.setIcon(skipForwardIcon);
    skipForwardButton.setAction(mediaPlayerActions.skipForwardAction());
    
    stopButton = new StandardButton();
    stopButton.setAction(mediaPlayerActions.playbackStopAction());

    fullscreenButton = new StandardButton();
    fullscreenButton.setIcon(fullscreenIcon);
    snapshotButton = new StandardButton();
    snapshotButton.setAction(mediaPlayerActions.videoSnapshotAction());
    
    cutStartButton = new StandardButton();
    cutStartButton.setAction(mediaPlayerActions.cutStartAction());
    cutStartButton.setEnabled(false);
    
    cutEndButton = new StandardButton();
    cutEndButton.setAction(mediaPlayerActions.cutEndAction());
    
    muteButton = new StandardButton();
    muteButton.setAction(mediaPlayerActions.muteAction());
    muteButton.setIcon(volumeHighIcon);
    volumeSlider = new JSlider();
    volumeSlider.setMinimum(0);
    volumeSlider.setMaximum(10);

    setLayout(new MigLayout("fill, insets 0 0 0 0", "[]12[]12[]0[]12[]0[]12[]0[]push[]0[]", "[]"));

    add(playPauseButton);
    add(stopButton, "sg 1");
    add(skipBackButton, "sg 1");
    add(skipForwardButton, "sg 1");
    add(fullscreenButton, "sg 1");
    add(snapshotButton, "sg 1");
    add(cutStartButton, "sg 1");
    add(cutEndButton, "sg 1");
    add(muteButton, "sg 1");
    add(volumeSlider, "wmax 100");

    volumeSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        application().mediaPlayer().setVolume(volumeSlider.getValue() / 10d);
      }
    });

    fullscreenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //WE HAVE TO GO BACK HERE!
        //application().mediaPlayer().toggleFullScreen();
      }
    });
  }
  
  private IVideoSlice currentSlice;
  
  private void checkPosition(long time) {
    cutEndButton.setEnabled(currentSlice != null && time > currentSlice.start());
  }

  @Subscribe
  public void onSelectVideoSlice(SelectVideoSliceEvent event) {
    this.currentSlice = event.getSlice();
  }

  @Subscribe
  public void onTick(TickEvent tick) {
    checkPosition(application().mediaPlayer().position());
  }

  @Subscribe
  public void onPlaying(PlayingEvent event) {
    cutStartButton.setEnabled(true);
    playPauseButton.setIcon(pauseIcon);
  }

  @Subscribe
  public void onPaused(PausedEvent event) {
    playPauseButton.setIcon(playIcon); 
  }

  @Subscribe
  public void onStopped(StoppedEvent event) {
    cutStartButton.setEnabled(false);
    playPauseButton.setIcon(playIcon); 
  }
  
  @Subscribe
  public void onVolume(VolumeEvent e) {
    volumeSlider.setValue((int)(e.volume() * 10d));
  }
  
  @Subscribe
  public void onMute(MuteEvent e) {
    if (e.isMute())
      muteButton.setIcon(volumeMutedIcon);
    else
      muteButton.setIcon(volumeHighIcon);
  }
}
