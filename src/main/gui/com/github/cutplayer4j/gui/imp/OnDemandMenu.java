
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
