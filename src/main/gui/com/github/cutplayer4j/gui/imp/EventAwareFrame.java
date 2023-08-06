
package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.Image;

import com.github.utils4j.gui.imp.SimpleFrame;

public abstract class EventAwareFrame extends SimpleFrame {

  public EventAwareFrame(String title, Image icon) {
    super(title, icon);
    application().subscribe(this);
  }
}
