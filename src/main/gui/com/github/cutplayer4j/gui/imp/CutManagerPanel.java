package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import javax.swing.JPanel;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.CutEndEvent;
import com.github.cutplayer4j.event.CutStartEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.gui.ICutManager;
import com.github.videohandler4j.gui.imp.CutPanel;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

public class CutManagerPanel extends JPanel implements ICutManager {
  
  private static final Color SELECTED = new Color(234, 248, 229);
  
  private static final Random RANDOM = new Random();
  
  private CutPanel selectedPanel;
  
  private File currentMedia;
  
  private Runnable onNoSlice, onFirstSlice;
  
  public CutManagerPanel(Runnable onNoSlice, Runnable onFirstSlice) {
    this.onNoSlice = onNoSlice;
    this.onFirstSlice = onFirstSlice;
    
    setLayout(new MigLayout());
    for(int s = 1; s < 4; s++) {
      addCutPanel(RANDOM.nextInt(15 * 60 * 1000));
    }
    onSelected(selectedPanel);
    application().subscribe(this);
  }

  private void addCutPanel(long start) {
    addCutPanel(start, Long.MAX_VALUE);
  }
  
  private void addCutPanel(long start, long end) {
    add(selectedPanel = new CutPanel(start, end)
      .setOnSelected(this::onSelected)
      .setOnDoSelect(this::onDoSelect)
      .setOnClosed(this::onClosed)
      .setOnPlay(this::onPlaying)
      .setOnSave(this::onSaved)
      .setOnStop(this::onStoped)
      , "wrap");
  }

  protected void onStoped(CutPanel panel) {
    System.out.println("onStoped");
    IMediaPlayer player = application().mediaPlayer();
    player.setPosition(panel.start());
    player.pause();
  }
  
  protected void onPlaying(CutPanel panel) {
    System.out.println("onPlaying");
    IMediaPlayer player = application().mediaPlayer();
    if (!player.isPlaying()) {
      player.play();
    }
    player.setPosition(panel.start());
  }

  protected void onClosed(CutPanel panel) {
    System.out.println("onClosed");
    if (selectedPanel == panel) {
      //se remover enquanto esstiver salvando?
      selectedPanel = null;
    }
    remove(panel);
    updateUI();
    if (getComponentCount() == 0) {
      onNoSlice.run();
    }
  }
  
  protected void onSelected(CutPanel panel) {
    System.out.println("onSelected");
    CutPanel previous = selectedPanel;
    onDoSelect(panel);
    if (previous == panel) {
      previous.setBackground(null);
      selectedPanel = null;
    }
    onStoped(panel);
  }
  
  protected void onDoSelect(CutPanel panel) {
    System.out.println("onDoSelected");
    int total = getComponentCount();
    for(int i = 0; i < total; i++) {
      getComponent(i).setBackground(null);
    }
    panel.setBackground(SELECTED);
    selectedPanel = panel;
  }

  @Subscribe
  protected void onCutStart(CutStartEvent event) {
    System.out.println("onCutStart");
    long time = event.getTime();
    addCutPanel(time);
    onDoSelect(selectedPanel);
    if (getComponentCount() == 1) {
      onFirstSlice.run();
    }
    updateUI();
  }
  
  @Subscribe
  protected void onCutEnd(CutEndEvent event) {
    System.out.println("onCutEnd");
    long time = event.getTime();
    if (selectedPanel != null) {
      selectedPanel.setEnd(time);
    }
  }
  
  @Subscribe
  public void onPlaying(PlayingEvent e) {
    currentMedia = e.getFile();
  }
  
  protected void onSaved(CutPanel panel) {
    if (currentMedia == null) {
      return;
    }
    panel.save(currentMedia);
  }
}
