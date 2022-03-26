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

import java.awt.Image;
import java.awt.event.ActionEvent;

import com.github.cutplayer4j.event.ShutdownEvent;
import com.google.common.eventbus.Subscribe;

public abstract class ShutdownAwareFrame extends EventAwareFrame {

  private boolean wasShown;

  public ShutdownAwareFrame(String title, Image icon) {
    super(title, icon);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        onEscPressed(null);
      }
    });
  }

  @Override
  protected void onEscPressed(ActionEvent e) {
    application().post(ShutdownEvent.INSTANCE);
    super.onEscPressed(e);
    if (e != null) {
      System.exit(0);
    }
  }
  
  @Override
  public final void setVisible(boolean b) {
    super.setVisible(b);
    if (b) {
      this.wasShown = true;
    }
  }

  @Subscribe
  public final void onShutdown(ShutdownEvent event) {
    onShutdown();
  }

  protected final boolean wasShown() {
    return wasShown;
  }
  
  protected void onShutdown() {
  }
}
