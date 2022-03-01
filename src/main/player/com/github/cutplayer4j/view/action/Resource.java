package com.github.cutplayer4j.view.action;

import static com.github.cutplayer4j.imp.CutPlayer4J.resources;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public final class Resource {

  private final String id;

  public static Resource resource(String id) {
    return new Resource(id);
  }

  private Resource(String id) {
    this.id = id;
  }

  public String name() {
    if (!resources().containsKey(id))
      return null;
    return resources().getString(id);
  }

  public Integer mnemonic() {
    String key = id + ".mnemonic";
    if (!resources().containsKey(key)) 
      return null;
    return Integer.valueOf(resources().getString(key).charAt(0));
  }

  public KeyStroke shortcut() {
    String key = id + ".shortcut";
    if (!resources().containsKey(key))
      return null;
    return KeyStroke.getKeyStroke(resources().getString(key));
  }

  public String tooltip() {
    String key = id + ".tooltip";
    if (!resources().containsKey(key))
      return null;
    return resources().getString(key);
  }

  public Icon menuIcon() {
    String key = id + ".menuIcon";
    if (!resources().containsKey(key))
      return null;
    return new ImageIcon(getClass().getResource("/icons/actions/" + resources().getString(key) + ".png"));
  }

  public Icon buttonIcon() {
    String key = id + ".buttonIcon";
    if (!resources().containsKey(key))
      return null;
    return new ImageIcon(getClass().getResource("/icons/buttons/" + resources().getString(key) + ".png"));
  }
}
