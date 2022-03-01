package com.github.cutplayer4j.gui.imp;

import static com.github.utils4j.imp.Throwables.tryCall;
import static com.github.utils4j.imp.Throwables.tryRuntime;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

import com.github.cutplayer4j.IMediaPlayer;
import com.github.cutplayer4j.gui.IPlayerListener;
import com.github.utils4j.imp.Throwables;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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

  private IPlayerListener playerListener;
  private MediaPlayer player;

  public JFXMediaPlayer(IPlayerListener playerListener) {
    this.playerListener = playerListener;
  }

  private boolean isAlive() {
    return player != null;
  }

  @Override
  public boolean toggleMute() {
    if (isAlive()) {
      player.setMute(!player.isMute());
      return player.isMute();
    }
    return false;
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
  public void skipTime(long deltaSeconds) {
    if (isAlive()) {
      player.seek(Duration.seconds(player.getCurrentTime().toSeconds() + deltaSeconds));
    }
  }

  @Override
  public BufferedImage snapshots() {
    if (!isAlive())
      return null;
    AtomicReference<BufferedImage> out = new AtomicReference<>();
    runAndWait(() -> {
      final Media media = player.getMedia();
      int width = media.getWidth();
      int height = media.getHeight();
      WritableImage image = new WritableImage(width, height);
      MediaView mediaView = new MediaView();
      mediaView.setFitWidth(width);
      mediaView.setFitHeight(height);
      mediaView.setMediaPlayer(player);
      mediaView.snapshot(null, image);
      out.set(SwingFXUtils.fromFXImage(image, null));
    });
    return out.get();
  }

  private static void runAndWait(Runnable runnable) {
    tryRuntime(() -> {
      if (Platform.isFxApplicationThread()) {
        runnable.run();
      } else {
        FutureTask<Object> futureTask = new FutureTask<>(runnable, null);
        Platform.runLater(futureTask);
        futureTask.get();
      }
    });
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
  public boolean play(String uri) {
    if (isAlive()) {
      player.stop();
      player.dispose();
      player = null;
    }
    try {
      player = new MediaPlayer(new Media(uri));
    }catch(Exception e) {
      player = null;
      playerListener.error();
      return false;
    }
    MediaView mediaView = new MediaView(player);
    mediaView.fitHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
    mediaView.fitWidthProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
    mediaView.setPreserveRatio(true);
    StackPane root = new StackPane();
    root.getChildren().add(mediaView);
    final Scene scene = new Scene(root);
    scene.setFill(javafx.scene.paint.Color.BLACK);
    setScene(scene);
    doAttach();
    play();
    return true;
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

  private void doAttach() {
    if (!isAlive())
      return;
    URI uri = tryCall(() -> new URI(player.getMedia().getSource()), (URI)null);
    player.setOnPaused(playerListener::paused);
    player.setOnStopped(playerListener::stopped);
    player.setOnPlaying(() -> playerListener.playing(uri));
    player.setOnReady(playerListener::ready);
    player.setOnError(playerListener::error);
    player.setOnEndOfMedia(playerListener::finished);
  }
}
