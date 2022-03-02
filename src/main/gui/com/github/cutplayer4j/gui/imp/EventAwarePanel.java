package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import com.github.utils4j.gui.imp.AbstractPanel;

public abstract class EventAwarePanel extends AbstractPanel {

  public EventAwarePanel() {
    application().subscribe(this);
  }
}
