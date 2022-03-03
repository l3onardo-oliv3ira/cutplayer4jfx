package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.resources;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JLabel;

import com.github.utils4j.gui.imp.JEscDialog;
import com.github.utils4j.imp.Throwables;

import net.miginfocom.swing.MigLayout;

final class AboutDialog extends JEscDialog {

  AboutDialog(Window owner) {
    super(owner, resources().getString("dialog.about"), Dialog.ModalityType.DOCUMENT_MODAL);

    setLayout(new MigLayout("insets 15, fillx", "[shrink]30[shrink][grow]", "[]20[]10[]10[]10[]10[]10[]10[]"));
    getContentPane().setBackground(Color.white);

    JLabel logoLabel = new JLabel();
    logoLabel.setIcon(Images.CUTPLAYER.asIcon().orElse(null));

    JLabel applicationLabel = new JLabel();
    applicationLabel.setFont(applicationLabel.getFont().deriveFont(30.0f));
    applicationLabel.setText(resources().getString("dialog.about.application"));

    JLabel resumeLabel = new JLabel();
    resumeLabel.setText(resources().getString("dialog.about.resume"));

    JLabel githubLabel = new JLabel();
    String uri = resources().getString("dialog.about.github");
    githubLabel.setText("<html>GitHub: <u>" + uri + "</u></html>");
    githubLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    githubLabel.setForeground(Color.BLUE);
    githubLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Throwables.tryRun(() -> {
          Desktop.getDesktop().browse(new URI(uri));
        });
      }
    });
    
    JLabel inspiration = new JLabel();
    inspiration.setText(resources().getString("dialog.about.inspiration"));

    JLabel labelIcons = new JLabel();
    labelIcons.setText(resources().getString("dialog.about.icons"));

    JLabel focusLabel = new JLabel();
    focusLabel.setText(resources().getString("dialog.about.focus"));

    JLabel applicationVersionLabel = new JLabel();
    applicationVersionLabel.setText(resources().getString("dialog.about.applicationVersion"));

    add(logoLabel, "shrink, top, spany 8");
    add(applicationLabel, "grow, spanx 2, wrap");
    add(resumeLabel, "grow, spanx 2, wrap");
    add(githubLabel, "grow, spanx 2, wrap");
    add(inspiration, "grow, spanx 2, wrap");
    add(labelIcons, "grow, spanx 2, wrap");
    add(focusLabel, "grow, spanx 2, wrap");
    add(applicationVersionLabel, "");
    pack();
    setResizable(false);
  }
}
