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
import static com.github.cutplayer4j.view.action.ResourceAction.resource;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import com.github.cutplayer4j.event.OpenRecentEvent;
import com.github.utils4j.gui.IResourceAction;
import com.github.utils4j.gui.imp.StandardAction;

final class RecentMediaMenu extends OnDemandMenu {

  RecentMediaMenu(IResourceAction resource) {
    super(resource, true);
  }

  @Override
  protected final void onPrepareMenu(JMenu menu) {
    List<String> mrls = application().recentMedia();
    if (!mrls.isEmpty()) {
      int i = 1;
      for (String mrl : mrls) {
        menu.add(new PlayRecentAction(i++, mrl));
      }
      menu.add(new JSeparator());
    }
    menu.add(new ClearRecentMediaAction());
  }

  private class PlayRecentAction extends AbstractAction {

    private final File file;

    public PlayRecentAction(int number, String mrl) {
      super(String.format("%d: %s", number, mrl));
      IResourceAction resource = resource("menu.media.item.recent.item.video");
      putValue(Action.SMALL_ICON, resource.menuIcon());
      putValue(Action.LARGE_ICON_KEY, resource.buttonIcon());
      putValue(Action.MNEMONIC_KEY, number < 10 ? number : 0);
      putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(String.format("control %d", number < 10 ? number : 0)));
      this.file = new File(mrl);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      application().post(new OpenRecentEvent(file));
    }
  }

  private class ClearRecentMediaAction extends StandardAction {

    public ClearRecentMediaAction() {
      super(resource("menu.media.item.recent.item.clear"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      application().clearRecentMedia();
    }
  }
}
