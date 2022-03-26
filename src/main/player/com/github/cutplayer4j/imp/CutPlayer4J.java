/*
* MIT License
* 
* Copyright (c) 2022 Leonardo de Lima Oliveira
* 
* https://github.com/l3onardo-oliv3ira
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/


package com.github.cutplayer4j.imp;

import static com.github.utils4j.gui.imp.SwingTools.invokeLater;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

import com.github.cutplayer4j.ICutPlayer4J;
import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.event.TickEvent;
import com.github.cutplayer4j.gui.IMediaPlayerViewer;
import com.github.cutplayer4j.gui.imp.MediaPlayerViewer;
import com.github.cutplayer4j.view.action.mediaplayer.MediaPlayerActions;
import com.github.utils4j.gui.imp.DefaultFileChooser;
import com.github.utils4j.imp.Services;
import com.github.utils4j.imp.Strings;
import com.google.common.eventbus.EventBus;

public class CutPlayer4J implements ICutPlayer4J {

  private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("strings/cutplayer4j");

  private static final int MAX_RECENT_MEDIA_SIZE = 10;
  
  private static final JFileChooser FILE_CHOOSER = new DefaultFileChooser();
  
  private static final class CutPlayer4JHolder {
    private static final CutPlayer4J INSTANCE = new CutPlayer4J();
  }

  public static CutPlayer4J application() {
    return CutPlayer4JHolder.INSTANCE;
  }

  public static ResourceBundle resources() {
    return RESOURCE_BUNDLE;
  }
  
  public static JFileChooser fileChooser() {
    return FILE_CHOOSER;
  }

  private final EventBus eventBus;

  private final IMediaPlayerViewer playerViewer;
  
  private final MediaPlayerActions mediaPlayerActions;

  private final ScheduledExecutorService tickService = Executors.newSingleThreadScheduledExecutor();

  private final Deque<String> recentMedia = new ArrayDeque<>(MAX_RECENT_MEDIA_SIZE);

  private CutPlayer4J() {
    eventBus = new EventBus();
    playerViewer = new MediaPlayerViewer();
    mediaPlayerActions = new MediaPlayerActions();
    tickService.scheduleWithFixedDelay(() -> eventBus.post(TickEvent.INSTANCE), 3, 500, TimeUnit.MILLISECONDS);
  }

  public void closeApplication() {
    playerViewer.close();
    Services.shutdownNow(tickService);
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

  public IMediaPlayerViewer playerViewer() {
    return playerViewer;
  }
  
  public IMediaPlayer mediaPlayer() {
    return playerViewer.mediaPlayer();
  }

  public MediaPlayerActions mediaPlayerActions() {
    return mediaPlayerActions;
  }

  public void addRecentMedia(String mrl) {
    if (Strings.hasText(mrl) && new File(mrl).exists()) {
      if (!recentMedia.contains(mrl)) {
        recentMedia.addFirst(mrl);
        while (recentMedia.size() > MAX_RECENT_MEDIA_SIZE) {
          recentMedia.pollLast();
        }
      }
    }
  }

  public List<String> recentMedia() {
    return new ArrayList<>(recentMedia);
  }
  
  public Optional<String> firstRecent() {
    return Optional.ofNullable(recentMedia.peekFirst());
  }

  public void clearRecentMedia() {
    recentMedia.clear();
  }
}
