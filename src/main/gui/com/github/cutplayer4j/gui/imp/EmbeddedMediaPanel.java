package com.github.cutplayer4j.gui.imp;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.gui.IMediaPlayerViewer;

public final class EmbeddedMediaPanel extends JPanel implements IMediaPlayerViewer {

  private static final String IDLE = "idle";

  private static final String ACTIVE = "active";

  private final CardLayout cardLayout;

  private JFXMediaPlayer player = new JFXMediaPlayer();

  public EmbeddedMediaPanel() {
    cardLayout = new CardLayout();
    setLayout(cardLayout);
    add(new ImagePane(ImagePane.Mode.CENTER, getClass().getResource("/cutplayer-black.png"), 0.3f), IDLE);
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
}
