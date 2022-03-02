package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.resources;
import static java.text.MessageFormat.format;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.github.cutplayer4j.gui.imp.ImagePane.Mode;
import com.github.utils4j.imp.Strings;
import com.google.common.io.Files;

import net.miginfocom.swing.MigLayout;

public class SnapshotView extends JFrame {

  private static final String DEFAULT_FILE_EXTENSION = "png";

  private final JFileChooser fileChooser = new JFileChooser();

  private final BufferedImage image;

  public SnapshotView(BufferedImage image) {
    this.image = image;
    setTitle(resources().getString("dialog.snapshot.title"));
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(new ImagePane(Mode.DEFAULT, image, 1.0f), BorderLayout.CENTER);
    contentPane.add(new ActionPane(), BorderLayout.SOUTH);
    setContentPane(contentPane);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    pack();
    setLocationByPlatform(true);
    setVisible(true);
  }

  private class ActionPane extends JPanel {

    private ActionPane() {
      setLayout(new MigLayout("fillx", "push[]", "[]"));
      JButton saveButton = new JButton("Save");
      add(saveButton);
      saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          onSave();
        }
      });
    }
  }
  
  private void onSave() {
    if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
      File file = fileChooser.getSelectedFile();
      try {
        String ext = Files.getFileExtension(file.getName()).toLowerCase();
        if (!Strings.hasText(ext)) {
          file = new File(file.getAbsolutePath() + "." + DEFAULT_FILE_EXTENSION);
          ext = DEFAULT_FILE_EXTENSION;
        }
        boolean wrote = ImageIO.write(image, ext, file);
        if (!wrote) {
          JOptionPane.showMessageDialog(this, 
            format(
              resources().getString("error.saveImage"), 
              file.toString(), 
              format(resources().getString("error.saveImageFormat"), ext)
            ), 
            resources().getString("dialog.saveImage"), 
            ERROR_MESSAGE
          );
        }
      }
      catch (IOException e) {
        JOptionPane.showMessageDialog(
          this, 
          format(resources().getString("error.saveImage"), 
            file.toString(), 
            e.getMessage()
          ), 
          resources().getString("dialog.saveImage"), 
          JOptionPane.ERROR_MESSAGE
        );
      }
    }
  }
}
