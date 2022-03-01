package com.github.cutplayer4j.gui.imp;

import java.awt.image.BufferedImage;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.gui.IMediaPlayerEventListener;

import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class JFXMediaPlayer extends JFXPanel implements IMediaPlayer {

  private IMediaPlayerEventListener listener;
  private MediaPlayer player;

  public JFXMediaPlayer() {
  }
  
  private boolean isAlive() {
    return player != null;
  }
  
  @Override
  public void mute() {
    if (isAlive()) {
      player.setMute(true);
    }
  }

  @Override
  public void pause() {
    if (isAlive()) {
      player.pause();
    }
  }

  @Override
  public boolean isPlaying() {
    if (!isAlive())
      return false;
    return MediaPlayer.Status.PLAYING == player.getStatus();
  }

  @Override
  public void play() {
    if (isAlive()) {
      player.play();
    }
  }

  @Override
  public void setRate(float rate) {
    if (isAlive()) {
      player.setRate(rate);
    }
  }

  @Override
  public void skipTime(long delta) {
    if (isAlive()) {
      Duration current = player.getCurrentTime();
      player.seek(delta > 0 ? current.add(Duration.seconds(delta)) : current.subtract(Duration.seconds(delta)));
    }
  }

  @Override
  public BufferedImage snapshots() {
    if (!isAlive())
      return null;
    final Media media = player.getMedia();
    int width = media.getWidth();
    int height = media.getHeight();
    WritableImage image = new WritableImage(width, height);
    MediaView mediaView = new MediaView();
    mediaView.setFitWidth(width);
    mediaView.setFitHeight(height);
    mediaView.setMediaPlayer(player);
    mediaView.snapshot(null, image);
    return SwingFXUtils.fromFXImage(image, null);
  }

  @Override
  public void stop() {
    if (isAlive()) {
      player.stop();
    }
  }

  @Override
  public double volume() {
    if (!isAlive())
      return 0;
    return player.getVolume();
  }

  @Override
  public void setVolume(double volume) {
    if (isAlive()) {
      player.setVolume(volume);
    }
  }

  @Override
  public void play(String uri) {
    if (isAlive()) {
      player.stop();
      player.dispose();
    }
    player = new MediaPlayer(new Media(uri));
    MediaView mediaView = new MediaView(player);
    mediaView.setPreserveRatio(true);
    StackPane root = new StackPane();
    root.getChildren().add(mediaView);
    final Scene scene = new Scene(root);
    scene.setFill(javafx.scene.paint.Color.BLACK);
    setScene(scene);
    doAttach();
    play();
  }

  @Override
  public void setPosition(long position) {
    if (isAlive()) {
      player.seek(Duration.millis(position));
    }
  }

  @Override
  public long position() {
    if (!isAlive())
      return 0;
    return (long)player.getCurrentTime().toMillis();
  }
  
  @Override
  public long duration() {
    if (!isAlive())
      return 0;
    return (long)player.getTotalDuration().toMillis();
  }

  @Override
  public void toggleFullScreen() {
    // TODO Auto-generated method stub
  }

  @Override
  public void attachListener(IMediaPlayerEventListener listener) {
    this.listener = listener;
    doAttach();
  }

  private void doAttach() {
    if (!isAlive())
      return;
    player.setOnPaused(listener::paused);
    player.setOnStopped(listener::stopped);
    player.setOnPlaying(listener::playing);
    player.setOnReady(listener::mediaPlayerReady);
    player.setOnError(listener::error);
    player.setOnEndOfMedia(listener::finished);
  }
}
