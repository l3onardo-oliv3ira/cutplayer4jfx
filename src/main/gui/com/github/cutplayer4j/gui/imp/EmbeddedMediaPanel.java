package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.gui.imp.Images.CUTPLAYER_BLACK;
import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.cutplayer4j.imp.CutPlayer4J.fileChooser;
import static com.github.cutplayer4j.imp.CutPlayer4J.resources;
import static com.github.utils4j.imp.SwingTools.invokeLater;

import java.awt.CardLayout;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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

public final class EmbeddedMediaPanel extends JPanel implements IMediaPlayerViewer {

  private static final String IDLE = "idle";

  private static final String ACTIVE = "active";

  private final CardLayout cardLayout;

  private JFXMediaPlayer player = new JFXMediaPlayer(new PlayerListener());

  public EmbeddedMediaPanel() {
    cardLayout = new CardLayout();
    setLayout(cardLayout);
    add(new ImagePane(ImagePane.Mode.CENTER, CUTPLAYER_BLACK.asBuffer().orElse(null), 0.3f), IDLE);
    add(player, ACTIVE);
  }

  @Override
  public void showIdle() {
    cardLayout.show(this, IDLE);
  }
  
  @Override
  public void showVideo() {
    cardLayout.show(this, ACTIVE);
  }

  @Override
  public IMediaPlayer mediaPlayer() {
    return player;
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
          EmbeddedMediaPanel.this, 
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
