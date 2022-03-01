package com.github.cutplayer4j.gui.imp;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.gui.IMediaPlayerProvider;

public final class EmbeddedMediaPanel extends JPanel implements IMediaPlayerProvider {

  private static final String IDLE = "idle";

  private static final String ACTIVE = "active";

  private final CardLayout cardLayout;

  private JFXMediaPlayer player = new JFXMediaPlayer();

  public EmbeddedMediaPanel() {
    cardLayout = new CardLayout();
    setLayout(cardLayout);
    add(new ImagePane(ImagePane.Mode.CENTER, getClass().getResource("/cutplayer-black.png"), 0.3f), IDLE);
    add(player, ACTIVE);
    showIdle();
  }

  public void showIdle() {
    cardLayout.show(this, IDLE);
  }
  
  public void showActive() {
    cardLayout.show(this, ACTIVE);
  }

  @Override
  public IMediaPlayer mediaPlayer() {
    return player;
  }
}
