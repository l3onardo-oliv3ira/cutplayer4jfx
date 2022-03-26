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

import static com.github.cutplayer4j.gui.imp.Images.CUTPLAYER_BLACK;
import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.cutplayer4j.imp.CutPlayer4J.fileChooser;
import static com.github.cutplayer4j.imp.CutPlayer4J.resources;
import static com.github.utils4j.gui.imp.SwingTools.invokeLater;

import java.awt.CardLayout;
import java.io.File;
import java.net.URI;
import java.text.MessageFormat;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.FinishedEvent;
import com.github.cutplayer4j.event.PausedEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.event.ReadyEvent;
import com.github.cutplayer4j.event.StoppedEvent;
import com.github.cutplayer4j.gui.IMediaPlayerViewer;
import com.github.cutplayer4j.gui.IPlayerListener;

public final class MediaPlayerViewer implements IMediaPlayerViewer {

  private static final String IDLE = "idle";

  private static final String ACTIVE = "active";

  private final JPanel panel = new JPanel();
  
  private final CardLayout cardLayout;

  private JFXMediaPlayer player = new JFXMediaPlayer(new PlayerListener());

  public MediaPlayerViewer() {
    cardLayout = new CardLayout();
    panel.setLayout(cardLayout);
    panel.add(new ImagePane(ImagePane.Mode.CENTER, CUTPLAYER_BLACK.asBuffer().orElse(null), 0.3f), IDLE);
    panel.add(player, ACTIVE);
  }
  
  @Override
  public JPanel asPanel() {
    return panel;
  }

  @Override
  public void showIdle() {
    cardLayout.show(panel, IDLE);
  }
  
  @Override
  public void showVideo() {
    cardLayout.show(panel, ACTIVE);
  }

  @Override
  public IMediaPlayer mediaPlayer() {
    return player;
  }
  
  @Override
  public void close() {
    player.close();
  }
  
  private class PlayerListener implements IPlayerListener {

    @Override
    public void playing(URI source) {
      invokeLater(() -> {
        showVideo();
        application().post(new PlayingEvent(source));
      });
    }

    @Override
    public void paused() {
      invokeLater(() -> {
        application().post(PausedEvent.INSTANCE);
      });
    }

    @Override
    public void stopped() {
      invokeLater(() -> {
        showIdle();
        application().post(StoppedEvent.INSTANCE);
      });
    }

    @Override
    public void finished() {
      invokeLater(() -> {
        application().post(FinishedEvent.INSTANCE);
      });
    }

    @Override
    public void error() {
      invokeLater(() ->  {
        showIdle();
        application().post(StoppedEvent.INSTANCE);
        File selectedFile = fileChooser().getSelectedFile();
        JOptionPane.showMessageDialog(
          panel, 
          MessageFormat.format(
            resources().getString("error.errorEncountered"), selectedFile != null ? selectedFile.getAbsolutePath() : ""), 
            resources().getString("dialog.errorEncountered"), 
            JOptionPane.ERROR_MESSAGE
          );
      });
    }

    @Override
    public void ready() {
      invokeLater(() -> {
        application().post(ReadyEvent.INSTANCE);
      });
    }
  }
}
