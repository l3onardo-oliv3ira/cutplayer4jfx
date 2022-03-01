package com.github.cutplayer4j.imp;

import static com.github.utils4j.imp.SwingTools.invokeLater;
import static com.github.utils4j.imp.Throwables.tryRuntime;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import static javax.swing.UIManager.setLookAndFeel;

import com.github.cutplayer4j.gui.ICutPlayer4JWindow;
import com.github.cutplayer4j.gui.imp.CutPlayer4JWindow;

public class CutPlayer4JApp {

  static { 
    tryRuntime(() -> setLookAndFeel(getSystemLookAndFeelClassName())); 
  }
  
  public static void main(String[] args) {
    invokeLater(() ->  new CutPlayer4JApp().start());
  }
  
  private void start() {
    ICutPlayer4JWindow window = new CutPlayer4JWindow();
    window.display();
    System.out.println("ACABOU");
  }
}