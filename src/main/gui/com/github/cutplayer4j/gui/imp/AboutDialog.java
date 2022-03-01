package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.resources;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

final class AboutDialog extends JDialog {

  AboutDialog(Window owner) {
    super(owner, resources().getString("dialog.about"), Dialog.ModalityType.DOCUMENT_MODAL);

    Properties properties = new Properties();
    try {
      properties.load(getClass().getResourceAsStream("/application.properties"));
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to load build.properties", e);
    }

    setLayout(new MigLayout("insets 30, fillx", "[shrink]30[shrink][grow]", "[]30[]10[]10[]30[]10[]10[]10[]0[]"));
    getContentPane().setBackground(Color.white);

    JLabel logoLabel = new JLabel();
    logoLabel.setIcon(new ImageIcon(getClass().getResource("/cutplayer.png")));

    JLabel applicationLabel = new JLabel();
    applicationLabel.setFont(applicationLabel.getFont().deriveFont(30.0f));
    applicationLabel.setText(resources().getString("dialog.about.application"));

    JLabel blurb1Label = new JLabel();
    blurb1Label.setText(resources().getString("dialog.about.blurb1"));

    JLabel blurb2Label = new JLabel();
    blurb2Label.setText(resources().getString("dialog.about.blurb2"));

    JLabel attribution1Label = new JLabel();
    attribution1Label.setText(resources().getString("dialog.about.attribution1"));

    JLabel applicationVersionLabel = new JLabel();
    applicationVersionLabel.setText(resources().getString("dialog.about.applicationVersion"));

    JLabel applicationVersionValueLabel = new ValueLabel();
    applicationVersionValueLabel.setText(properties.getProperty("application.version"));

    JLabel vlcjVersionLabel = new JLabel();
    vlcjVersionLabel.setText(resources().getString("dialog.about.vlcjVersion"));

    JLabel vlcVersionLabel = new JLabel();
    vlcVersionLabel.setText(resources().getString("dialog.about.vlcVersion"));

    JLabel nativeLibraryPathLabel = new JLabel();
    nativeLibraryPathLabel.setText(resources().getString("dialog.about.nativeLibraryPath"));

    add(logoLabel, "shrink, top, spany 8");
    add(applicationLabel, "grow, spanx 2, wrap");
    add(blurb1Label, "grow, spanx 2, wrap");
    add(blurb2Label, "grow, spanx 2, wrap");
    add(attribution1Label, "grow, spanx 2, wrap");
    add(applicationVersionLabel, "");
    add(applicationVersionValueLabel, "wrap");
    add(vlcjVersionLabel);

    getRootPane().registerKeyboardAction(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

    pack();
    setResizable(false);
  }

  private class ValueLabel extends JLabel {

    public ValueLabel() {
      setFont(getFont().deriveFont(Font.BOLD));
    }
  }
}
