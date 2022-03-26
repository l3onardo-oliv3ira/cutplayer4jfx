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


package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.utils4j.imp.Strings.trim;
import static com.github.utils4j.imp.Throwables.tryRun;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;

import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.event.TickEvent;
import com.github.videohandler4j.imp.TimeTools;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

final class StatusBar extends EventAwarePanel {

  private final JLabel titleLabel;

  private final JLabel sizeLabel;

  private final JLabel timeLabel;

  private long time;

  private long duration;

  StatusBar() {
    titleLabel = new BorderedStandardLabel();
    titleLabel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        tryRun(() -> {
          File f = new File(trim(titleLabel.getText()));
          if (f.exists()) {
            Desktop.getDesktop().open(f.getParentFile());
          }
        });
      }  
    });
    
    sizeLabel = new BorderedStandardLabel();
    timeLabel = new BorderedStandardLabel();

    setLayout(new MigLayout("fillx, insets 2 n n n", "[grow]16[][]", "[]"));
    add(titleLabel, "grow");
    add(sizeLabel);
    add(timeLabel);
  }

  void setTitle(String title) {
    titleLabel.setText(title);
  }

  void setSize(String size) {
    sizeLabel.setText(size);
  }

  void setTime(long time) {
    this.time = time;
  }
  
  void setFile(File file) {
    setTitle(file.getAbsolutePath());
    setSize(String.format("%.2fMB", file.length() / 1024d / 1024d) );
  }

  void setDuration(long duration) {
    this.duration = duration;
    refresh();
  }

  void clear() {
    titleLabel.setText(null);
    sizeLabel.setText(null);
    timeLabel.setText(null);
  }

  void refresh() {
    timeLabel.setText(String.format("%s/%s", TimeTools.toString(time), TimeTools.toString(duration)));
  }

  @Subscribe
  public void onTick(TickEvent tick) {
    setTime(application().mediaPlayer().position());
    refresh();
  }
  
  @Subscribe
  public void onPlaying(PlayingEvent e) {
    setDuration(application().mediaPlayer().duration());
    setFile(e.getFile());
  }
}
