package com.github.cutplayer4j.view;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.cutplayer4j.event.ShutdownEvent;
import com.google.common.eventbus.Subscribe;

public abstract class BasePanel extends JPanel {

  public BasePanel() {
    application().subscribe(this);
  }

  @Subscribe
  public final void onShutdown(ShutdownEvent event) {
    onShutdown();
  }
  
  protected final void onShutdown() {
  }

  protected class BigButton extends JButton {
    public BigButton() {
      setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      setHideActionText(true);
    }
  }

  protected class StandardButton extends JButton {
    public StandardButton() {
      setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
      setHideActionText(true);
    }
  }

  protected Icon newIcon(String name) {
    return new ImageIcon(getClass().getResource("/icons/buttons/" + name + ".png"));
  }
}
