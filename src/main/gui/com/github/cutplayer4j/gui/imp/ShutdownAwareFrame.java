package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.Image;

import com.github.cutplayer4j.event.ShutdownEvent;
import com.google.common.eventbus.Subscribe;

public abstract class ShutdownAwareFrame extends EventAwareFrame {

  private boolean wasShown;

  public ShutdownAwareFrame(String title, Image icon) {
    super(title, icon);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        application().post(ShutdownEvent.INSTANCE);
      }
    });
  }

  @Override
  public final void setVisible(boolean b) {
    super.setVisible(b);
    if (b) {
      this.wasShown = true;
    }
  }

  @Subscribe
  public final void onShutdown(ShutdownEvent event) {
    onShutdown();
  }

  protected final boolean wasShown() {
    return wasShown;
  }
  
  protected void onShutdown() {
  }
}