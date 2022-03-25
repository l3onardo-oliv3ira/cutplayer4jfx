package com.github.cutplayer4j.imp;

import static com.github.utils4j.gui.imp.SwingTools.invokeLater;

import java.io.File;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import com.github.cutplayer4j.gui.ICutPlayer4JWindow;
import com.github.cutplayer4j.gui.imp.CutPlayer4JWindow;
import com.github.utils4j.gui.imp.LookAndFeelsInstaller;
import com.github.utils4j.imp.Environment;

public class CutPlayer4JApp {

  private static final String ENVIRONMENT_VARIABLE_NAME = "CUTPLAYER4J_LOOKSANDFEELS";

  private static final CutPlayer4J APP; 
  
  static {
    LookAndFeelsInstaller.install(Environment.valueFrom(ENVIRONMENT_VARIABLE_NAME).orElse("undefined"));
    APP = CutPlayer4J.application();
  }
  
  public static void main(String[] args) {
    Stream.of(args).sorted(Comparator.reverseOrder()).forEach(APP::addRecentMedia);
    invokeLater(() ->  new CutPlayer4JApp().start());
  }
  
  private void start() {
    Optional<String> video = APP.firstRecent();
    ICutPlayer4JWindow window = new CutPlayer4JWindow(!video.isPresent());
    window.display();    
    if (video.isPresent()) {    
      window.open(new File(video.get()));
    }
  }
}