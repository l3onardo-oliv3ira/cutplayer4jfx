package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static java.util.Optional.ofNullable;

import java.awt.Color;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.CutEndEvent;
import com.github.cutplayer4j.event.CutStartEvent;
import com.github.cutplayer4j.event.PlayingEvent;
import com.github.cutplayer4j.event.SaveAllEvent;
import com.github.cutplayer4j.event.SelectVideoSliceEvent;
import com.github.cutplayer4j.gui.ICutManager;
import com.github.utils4j.gui.imp.DefaultFileChooser;
import com.github.utils4j.imp.function.Functions;
import com.github.videohandler4j.IVideoSliceView;
import com.github.videohandler4j.gui.imp.VideoSlicePanel;
import com.github.videohandler4j.imp.VideoSliceView;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

public class CutManagerPanel extends JPanel implements ICutManager {
  
  private static final Runnable NOTHING = Functions.EMPTY_RUNNABLE;
  
  private static final Color SELECTED = new Color(234, 248, 229);
  
  private IVideoSliceView selectedPanel = null;
  
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
    application().subscribe(this);
  }

  private IVideoSliceView addCutPanel(long start) {
    return addCutPanel(start, Long.MAX_VALUE);
  }
  
  private IVideoSliceView addCutPanel(long start, long end) {
    IVideoSliceView view;
    add((view = new VideoSliceView(start, end)
      .setOnSelected(this::onSelected)
      .setOnDoSelect(this::onDoSelect)
      .setOnClosed(this::onClosed)
      .setOnPlay(this::onPlaying)
      .setOnSave(this::onSaved)
      .setOnStop(this::onStoped))
      .asPanel(), 
      "wrap"
    );
    return view;
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
      application().post(new SelectVideoSliceEvent(selectedPanel = null));
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
      application().post(new SelectVideoSliceEvent(selectedPanel = null));
    }
    onStoped(panel);
  }
  
  protected void onDoSelect(IVideoSliceView panel) {
    int total = getComponentCount();
    for(int i = 0; i < total; i++) {
      getComponent(i).setBackground(null);
    }
    panel.asPanel().setBackground(SELECTED);
    if (selectedPanel != panel) {
      application().post(new SelectVideoSliceEvent(panel));
    }
    selectedPanel = panel;
  }

  @Subscribe
  protected void onCutStart(CutStartEvent event) {
    long time = event.getTime();
    onDoSelect(addCutPanel(time));
    if (getComponentCount() == 1) {
      onTouch.run();
    }
    updateUI();
  }
  
  @Subscribe
  protected void onCutEnd(CutEndEvent event) {
    if (selectedPanel != null) {
      selectedPanel.setEnd(event.getTime());
    }
  }
  
  @Subscribe
  public void onPlaying(PlayingEvent e) {
    currentMedia = e.getFile();
  }
  
  @Subscribe
  public void onSaveAll(SaveAllEvent e) {
    if (currentMedia != null) {
      File folder = selectFolder();
      if (folder != null) {
        int total = getComponentCount();
        for(int i = 0; i < total; i++) {
          VideoSlicePanel panel = (VideoSlicePanel)getComponent(i);
          panel.splitAndSave(currentMedia, folder);
        }
      }
    }
  }
  
  protected void onSaved(IVideoSliceView panel) {
    if (currentMedia != null) {
      File folder = selectFolder();
      if (folder != null) {
        panel.splitAndSave(currentMedia, folder);
      }
    }
  }
  
  private static File selectFolder() {
    JFileChooser chooser = new DefaultFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setDialogTitle("Selecione onde será gravado o vídeo");
    if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
      return chooser.getSelectedFile();
    }
    return null;
  }
}
