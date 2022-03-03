package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static java.util.Optional.ofNullable;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.CutEndEvent;
import com.github.cutplayer4j.event.CutStartEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.gui.ICutManager;
import com.github.utils4j.gui.imp.DefaultFileChooser;
import com.github.utils4j.imp.function.Functions;
import com.github.videohandler4j.IVideoSliceView;
import com.github.videohandler4j.imp.VideoSliceView;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

public class CutManagerPanel extends JPanel implements ICutManager {
  
  private static final Runnable NOTHING = Functions.EMPTY_RUNNABLE;
  
  private static final Color SELECTED = new Color(234, 248, 229);
  
  private static final Random RANDOM = new Random();
  
  private IVideoSliceView selectedPanel;
  
  private File currentMedia;
  
  private Runnable onTouch, onEmpty = onTouch = NOTHING;
  
  public CutManagerPanel() {
    this(NOTHING);
  }
    
  public CutManagerPanel(Runnable onEmpty) {
    this(onEmpty, NOTHING);
  }
  
  public CutManagerPanel(Runnable onEmpty, Runnable onTouch) {
    this.onEmpty = ofNullable(onEmpty).orElse(NOTHING);
    this.onTouch = ofNullable(onTouch).orElse(NOTHING);
    setLayout(new MigLayout());
//    for(int s = 1; s < 4; s++) {
//      addCutPanel(RANDOM.nextInt(15 * 60 * 1000));
//    }
//    onSelected(selectedPanel);
    application().subscribe(this);
  }

  private void addCutPanel(long start) {
    addCutPanel(start, Long.MAX_VALUE);
  }
  
  private void addCutPanel(long start, long end) {
    add((selectedPanel = new VideoSliceView(start, end)
      .setOnSelected(this::onSelected)
      .setOnDoSelect(this::onDoSelect)
      .setOnClosed(this::onClosed)
      .setOnPlay(this::onPlaying)
      .setOnSave(this::onSaved)
      .setOnStop(this::onStoped))
      .asPanel(), 
      "wrap"
    );
  }

  protected void onStoped(IVideoSliceView panel) {
    IMediaPlayer player = application().mediaPlayer();
    player.setPosition(panel.start());
    player.pause();
  }
  
  protected void onPlaying(IVideoSliceView panel) {
    IMediaPlayer player = application().mediaPlayer();
    if (!player.isPlaying()) {
      player.play();
    }
    player.setPosition(panel.start());
  }

  protected void onClosed(IVideoSliceView panel) {
    if (selectedPanel == panel) {
      //se remover enquanto esstiver salvando?
      selectedPanel = null;
    }
    remove(panel.asPanel());
    updateUI();
    if (getComponentCount() == 0) {
      onEmpty.run();
    }
  }
  
  protected void onSelected(IVideoSliceView panel) {
    IVideoSliceView previous = selectedPanel;
    onDoSelect(panel);
    if (previous == panel) {
      previous.asPanel().setBackground(null);
      selectedPanel = null;
    }
    onStoped(panel);
  }
  
  protected void onDoSelect(IVideoSliceView panel) {
    int total = getComponentCount();
    for(int i = 0; i < total; i++) {
      getComponent(i).setBackground(null);
    }
    panel.asPanel().setBackground(SELECTED);
    selectedPanel = panel;
  }

  @Subscribe
  protected void onCutStart(CutStartEvent event) {
    long time = event.getTime();
    addCutPanel(time);
    onDoSelect(selectedPanel);
    if (getComponentCount() == 1) {
      onTouch.run();
    }
    updateUI();
  }
  
  @Subscribe
  protected void onCutEnd(CutEndEvent event) {
    long time = event.getTime();
    if (selectedPanel != null) {
      selectedPanel.setEnd(time);
    }
  }
  
  @Subscribe
  public void onPlaying(PlayingEvent e) {
    currentMedia = e.getFile();
  }
  
  protected void onSaved(IVideoSliceView panel) {
    if (currentMedia != null) {
      JFileChooser chooser = new DefaultFileChooser();
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setDialogTitle("Selecione onde será gravado o vídeo");
      if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
        panel.splitAndSave(currentMedia, chooser.getSelectedFile());
      }
    }
  }
}
