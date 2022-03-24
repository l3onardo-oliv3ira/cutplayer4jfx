package com.github.cutplayer4j.imp;

import static com.github.utils4j.gui.imp.SwingTools.invokeLater;

import java.io.File;
import java.util.Optional;

import com.github.cutplayer4j.gui.ICutPlayer4JWindow;
import com.github.cutplayer4j.gui.imp.CutPlayer4JWindow;
import com.github.utils4j.gui.imp.LookAndFeelsInstaller;
import com.github.utils4j.imp.Environment;

public class CutPlayer4JApp {

  static final String ENVIRONMENT_VARIABLE_NAME = "CUTPLAYER4J_LOOKSANDFEELS";

  static {
    LookAndFeelsInstaller.install(Environment.valueFrom(ENVIRONMENT_VARIABLE_NAME).orElse("undefined"));
  }
  
  public static void main(String[] args) {
    CutPlayer4J app = CutPlayer4J.application();  
    if (args.length > 0) {
      app.clearRecentMedia();    
      for(String f: args) {
        app.addRecentMedia(f);
      }
    }   
    invokeLater(() ->  new CutPlayer4JApp().start());
  }
  
  private void start() {
    ICutPlayer4JWindow window = new CutPlayer4JWindow();
    window.display();    
    Optional<String> video = CutPlayer4J.application().firstRecent();
    if (video.isPresent()) {    
      window.open(new File(video.get()));
    }
  }
}