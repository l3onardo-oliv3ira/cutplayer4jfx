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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

public abstract class MouseMovementDetector {

  private final Component component;

  private final int timeout;

  private final MouseMotionListener mouseMotionListener = new ActivityListener();

  private Timer timer;

  private boolean started;

  private boolean moving;

  public MouseMovementDetector(Component component, int timeout) {
    this.component = component;
    this.timeout = timeout;
  }

  public void start() {
    if (!started) {
      timer = new Timer(timeout, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          timeout();
        }
      });
      component.addMouseMotionListener(mouseMotionListener);
      timer.start();
      timer.setRepeats(false);
      started = true;
      onStarted();
    }
    else {
//      throw new IllegalStateException("Already started");
      // FIXME
    }
  }

  public void stop() {
    if (started) {
      component.removeMouseMotionListener(mouseMotionListener);
      timer.stop();
      timer = null;
      started = false;
      onStopped();
    }
  }

  private void movement() {
    if (!moving) {
      moving = true;
      onMouseMoved();
    }
    timer.restart();
  }

  private void timeout() {
    moving = false;
    onMouseAtRest();
  }

  protected void onStarted() {
  }

  protected void onMouseAtRest() {
  }

  protected void onMouseMoved() {
  }

  protected void onStopped() {
  }

  private class ActivityListener extends MouseMotionAdapter {
    @Override
    public void mouseMoved(MouseEvent e) {
      movement();
    }
  }
}
