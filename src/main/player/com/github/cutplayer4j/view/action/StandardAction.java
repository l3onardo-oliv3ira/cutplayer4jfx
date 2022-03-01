package com.github.cutplayer4j.view.action;

import javax.swing.AbstractAction;
import javax.swing.Action;

@SuppressWarnings("serial")
public abstract class StandardAction extends AbstractAction {

  public StandardAction(Resource resource) {
    putValue(Action.NAME, resource.name());
    putValue(Action.MNEMONIC_KEY, resource.mnemonic());
    putValue(Action.ACCELERATOR_KEY, resource.shortcut());
    putValue(Action.SHORT_DESCRIPTION, resource.tooltip());
    putValue(Action.SMALL_ICON, resource.menuIcon());
    putValue(Action.LARGE_ICON_KEY, resource.buttonIcon());
  }

  public StandardAction(String name) {
    putValue(Action.NAME, name);
  }

  public final void select(boolean select) {
    putValue(Action.SELECTED_KEY, select);
  }
}
