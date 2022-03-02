package com.github.cutplayer4j.view.action;

import static com.github.cutplayer4j.imp.CutPlayer4J.resources;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.github.utils4j.gui.IResourceAction;

public final class ResourceAction implements IResourceAction {

  private final String id;

  public static IResourceAction resource(String id) {
    return new ResourceAction(id);
  }

  private ResourceAction(String id) {
    this.id = id;
  }

  @Override
  public String name() {
    if (!resources().containsKey(id))
      return null;
    return resources().getString(id);
  }

  @Override
  public Integer mnemonic() {
    String key = id + ".mnemonic";
    if (!resources().containsKey(key)) 
      return null;
    return Integer.valueOf(resources().getString(key).charAt(0));
  }

  @Override
  public KeyStroke shortcut() {
    String key = id + ".shortcut";
    if (!resources().containsKey(key))
      return null;
    return KeyStroke.getKeyStroke(resources().getString(key));
  }

  @Override
  public String tooltip() {
    String key = id + ".tooltip";
    if (!resources().containsKey(key))
      return null;
    return resources().getString(key);
  }

  @Override
  public Icon menuIcon() {
    String key = id + ".menuIcon";
    if (!resources().containsKey(key))
      return null;
    return toIcon(key, "actions");
  }

  @Override
  public Icon buttonIcon() {
    String key = id + ".buttonIcon";
    if (!resources().containsKey(key))
      return null;
    return toIcon(key, "buttons");
  }

  private Icon toIcon(String key, String type) {
    URL url = getClass().getResource("/icons/" + type + "/" + resources().getString(key) + ".png");
    if (url == null)
      return null;
    return new ImageIcon(url);
  }
}
