/*
* MIT License
* 
* Copyright (c) 2022 Leonardo de Lima Oliveira
* 
* https://github.com/l3onardo-oliv3ira
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/


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
import com.github.utils4j.imp.Threads;

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
      Threads.startAsync(window::pause, 500);
    }
  }
}
