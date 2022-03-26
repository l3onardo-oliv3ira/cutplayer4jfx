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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.cutplayer4j.event.SaveAllEvent;
import com.github.utils4j.gui.imp.AbstractPanel;

import net.miginfocom.swing.MigLayout;

public class SaveAllSlicesPanel extends AbstractPanel {
  private final Icon saveIcon = newIcon("save");
  
  private final JButton saveButton = new StandardButton();
  
  public SaveAllSlicesPanel() {
    super("/vh4j/icons/buttons/");
    setLayout(new BorderLayout());
    add(new JPanel(), BorderLayout.EAST);

    JPanel center = new JPanel();
    center.setLayout(new MigLayout());
    center.add(saveButton, "pushx, growx");
    saveButton.setIcon(saveIcon);
    saveButton.setText("Salvar todos os cortes...");
    saveButton.addActionListener(this::saveAll);
    add(center);
  }
  
  protected void saveAll(ActionEvent e) {
    application().post(SaveAllEvent.INSTANCE);
  }
}
