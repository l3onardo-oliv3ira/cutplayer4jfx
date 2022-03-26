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
    URL url = getClass().getResource("/cp4j/icons/" + type + "/" + resources().getString(key) + ".png");
    if (url == null)
      return null;
    return new ImageIcon(url);
  }
}
