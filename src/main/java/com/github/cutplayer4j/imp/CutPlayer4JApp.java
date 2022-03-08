package com.github.cutplayer4j.imp;

import static com.github.utils4j.gui.imp.SwingTools.invokeLater;

import com.github.cutplayer4j.gui.imp.CutPlayer4JWindow;
import com.github.utils4j.gui.imp.LookAndFeelsInstaller;
import com.github.utils4j.imp.Environment;

public class CutPlayer4JApp {

  static final String ENVIRONMENT_VARIABLE_NAME = "CUTPLAYER4J_LOOKSANDFEELS";

  static {
    LookAndFeelsInstaller.install(Environment.valueFrom(ENVIRONMENT_VARIABLE_NAME).orElse("undefined"));
  }
  
  public static void main(String[] args) {
    invokeLater(() ->  new CutPlayer4JApp().start());
  }
  
  private void start() {
    CutPlayer4JWindow window = new CutPlayer4JWindow();
    window.display();
  }
}