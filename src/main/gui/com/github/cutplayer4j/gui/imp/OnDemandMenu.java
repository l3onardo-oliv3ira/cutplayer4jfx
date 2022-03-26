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

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.github.utils4j.gui.IResourceAction;

public abstract class OnDemandMenu implements MenuListener {

  private final JMenu menu;

  private final boolean clearOnPrepare;

  public OnDemandMenu(IResourceAction resource) {
    this(resource, false);
  }

  public OnDemandMenu(IResourceAction resource, boolean clearOnPrepare) {
    this.menu = new JMenu(resource.name());
    this.menu.setMnemonic(resource.mnemonic());
    this.menu.addMenuListener(this);
    this.clearOnPrepare = clearOnPrepare;
    onCreateMenu(this.menu);
  }

  public final JMenu menu() {
    return menu;
  }

  @Override
  public final void menuSelected(MenuEvent e) {
    if (clearOnPrepare) {
      menu.removeAll();
    }
    onPrepareMenu(menu);
    menu.setEnabled(menu.getItemCount() > 0);
  }

  @Override
  public final void menuDeselected(MenuEvent e) {
  }

  @Override
  public final void menuCanceled(MenuEvent e) {
  }

  protected void onCreateMenu(JMenu menu) {
  }

  protected abstract void onPrepareMenu(JMenu menu);
}
