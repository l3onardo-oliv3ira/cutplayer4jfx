package com.github.cutplayer4j.gui.imp;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.cutplayer4j.view.BasePanel;

import net.miginfocom.swing.MigLayout;

public class CutPanel extends BasePanel {

  private final Icon playIcon = newIcon("play12");

  private final Icon pauseIcon = newIcon("pause12");
  
  private final Icon stopIcon = newIcon("stop");
  
  private final Icon saveIcon = newIcon("save");
  
  private final Icon closeIcon = newIcon("close");
  
  private final JTextField txtFragName;
  
  private final JLabel startTime;

  private final JLabel endTime;


  private final JButton playPauseButton;

  private final JButton stopButton;
  
  private final JLabel lengthTime;

  private final JButton closeButton;
  
  private final JButton saveButton;

  public CutPanel() {
    txtFragName = new JTextField();
    startTime = new JLabel("00:12:35");
    endTime = new JLabel("12:43:12");
    
    playPauseButton = new StandardButton();
    playPauseButton.setIcon(playIcon);
    stopButton = new StandardButton();
    stopButton.setIcon(stopIcon);
    
    lengthTime = new JLabel("04:12:00");
    
    saveButton = new StandardButton();
    saveButton.setIcon(saveIcon);
    
    closeButton = new StandardButton();
    closeButton.setIcon(closeIcon);
    
    
    setLayout(new MigLayout());
    add(txtFragName, "span, pushx, growx, wrap");
    add(startTime);
    add(endTime, "al right, wrap");
    
    add(playPauseButton, "split 4");
    add(stopButton);
    add(lengthTime);
    add(saveButton);
    add(closeButton, "al right");
  }
}
