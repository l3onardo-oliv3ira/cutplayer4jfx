package com.github.cutplayer4j.view;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.Image;

import com.github.cutplayer4j.event.ShutdownEvent;
import com.github.utils4j.imp.SimpleFrame;
import com.google.common.eventbus.Subscribe;

public abstract class ShutdownAwareFrame extends SimpleFrame {

  private boolean wasShown;

  public ShutdownAwareFrame(String title, Image icon) {
    super(title, icon);
    application().subscribe(this);
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