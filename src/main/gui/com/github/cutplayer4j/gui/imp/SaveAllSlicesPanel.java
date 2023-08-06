
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
