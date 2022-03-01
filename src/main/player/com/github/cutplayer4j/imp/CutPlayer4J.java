package com.github.cutplayer4j.imp;

import static com.github.utils4j.imp.SwingTools.invokeLater;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.cutplayer4j.ICutPlayer4J;
import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.TickEvent;
import com.github.cutplayer4j.gui.IMediaPlayerViewer;
import com.github.cutplayer4j.gui.imp.EmbeddedMediaPanel;
import com.github.cutplayer4j.view.action.mediaplayer.MediaPlayerActions;
import com.google.common.eventbus.EventBus;

public class CutPlayer4J implements ICutPlayer4J {

  private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("strings/cutplayer4j");

  private static final int MAX_RECENT_MEDIA_SIZE = 10;
  
  private static final class CutPlayer4JHolder {
    private static final CutPlayer4J INSTANCE = new CutPlayer4J();
  }

  public static CutPlayer4J application() {
    return CutPlayer4JHolder.INSTANCE;
  }

  public static ResourceBundle resources() {
    return RESOURCE_BUNDLE;
  }

  private final EventBus eventBus;

  private final EmbeddedMediaPanel mediaPlayerPanel;
  
  private final IMediaPlayer mediaPlayer;

  private final MediaPlayerActions mediaPlayerActions;

  private final ScheduledExecutorService tickService = Executors.newSingleThreadScheduledExecutor();

  private final Deque<String> recentMedia = new ArrayDeque<>(MAX_RECENT_MEDIA_SIZE);

  private CutPlayer4J() {
    eventBus = new EventBus();
    mediaPlayer = (mediaPlayerPanel = new EmbeddedMediaPanel()).mediaPlayer();
    mediaPlayerActions = new MediaPlayerActions();
    tickService.scheduleWithFixedDelay(() -> eventBus.post(TickEvent.INSTANCE), 2, 500, TimeUnit.MILLISECONDS);
  }

  public void subscribe(Object subscriber) {
    eventBus.register(subscriber);
  }
  
  public void unsubscribe(Object subscriber) {
    eventBus.unregister(subscriber);
  }

  public void post(Object event) {
    invokeLater(() -> eventBus.post(event));
  }

  public IMediaPlayerViewer mediaPlayerPanel() {
    return mediaPlayerPanel;
  }
  
  public IMediaPlayer mediaPlayer() {
    return mediaPlayer;
  }

  public MediaPlayerActions mediaPlayerActions() {
    return mediaPlayerActions;
  }

  public void addRecentMedia(String mrl) {
    if (!recentMedia.contains(mrl)) {
      recentMedia.addFirst(mrl);
      while (recentMedia.size() > MAX_RECENT_MEDIA_SIZE) {
        recentMedia.pollLast();
      }
    }
  }

  public List<String> recentMedia() {
    return new ArrayList<>(recentMedia);
  }

  public void clearRecentMedia() {
    recentMedia.clear();
  }
}
