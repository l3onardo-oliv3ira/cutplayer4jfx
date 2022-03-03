package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.resources;
import static java.text.MessageFormat.format;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JScrollPane;

import com.github.cutplayer4j.gui.imp.ImagePane.Mode;
import com.github.utils4j.imp.Strings;
import com.google.common.io.Files;

import net.miginfocom.swing.MigLayout;

public class SnapshotFrame extends JFrame {

  private static final String DEFAULT_FILE_EXTENSION = "png";

  private final BufferedImage image;

  public SnapshotFrame(BufferedImage image) {
    this.image = image;
    setTitle(resources().getString("dialog.snapshot.title"));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(600, 400);
    buildPanels();
    setLocationByPlatform(true);
    setVisible(true);
  }

  private void buildPanels() {
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(center(), BorderLayout.CENTER);
    contentPane.add(south(), BorderLayout.SOUTH);
    setContentPane(contentPane);
  }

  private Component south() {
    JPanel south = new JPanel();
    south.setLayout(new MigLayout("fillx", "push[]", "[]"));
    JButton saveButton = new JButton(resources().getString("dialog.snapshot.save.title"));
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSave();
      }
    });
    south.add(saveButton);
    return south;
  }

  private JScrollPane center() {
    JScrollPane centerPane = new JScrollPane();
    ImagePane imagePane = new ImagePane(Mode.DEFAULT, image, 1.0f);
    centerPane.setViewportView(imagePane);
    return centerPane;
  }

  private void onSave() {
    JFileChooser fileChooser = new JFileChooser();
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
          showFail(file, ext);
        }
      } catch (IOException e) {
        showFail(file, e);
      }
    }
  }

  private void showFail(File file, IOException e) {
    showMessageDialog(this, 
      format(resources().getString("error.saveImage"), 
        file.toString(), 
        e.getMessage()
      ), 
      resources().getString("dialog.saveImage"), 
      JOptionPane.ERROR_MESSAGE
    );
  }

  private void showFail(File file, String ext) {
    showMessageDialog(this, 
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
