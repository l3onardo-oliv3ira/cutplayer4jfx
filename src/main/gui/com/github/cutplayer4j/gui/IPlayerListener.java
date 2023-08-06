
package com.github.cutplayer4j.gui;

import java.net.URI;

public interface IPlayerListener {

  void ready();

  void error();

  void finished();

  void stopped();

  void paused();

  void playing(URI source);
}
