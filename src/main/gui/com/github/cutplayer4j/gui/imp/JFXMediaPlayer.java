package com.github.cutplayer4j.gui.imp;

import java.awt.image.BufferedImage;

import com.github.cutplayer4j.IMediaPlayer;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class JFXMediaPlayer extends JFXPanel implements IMediaPlayer {

  private MediaPlayer player;

  public JFXMediaPlayer() {
  }

  @Override
  public void mute() {
    Platform.runLater(() -> player.setMute(true));
  }

  @Override
  public void pause() {
    Platform.runLater(() -> player.pause());
  }

  @Override
  public boolean isPlaying() {
    return MediaPlayer.Status.PLAYING == player.getStatus();
  }

  @Override
  public void play() {
    Platform.runLater(player::play);
  }

  @Override
  public void setRate(float rate) {
    Platform.runLater(() -> player.setRate(rate));
  }

  @Override
  public void skipTime(long delta) {
    Platform.runLater(() -> {
      Duration current = player.getCurrentTime();
      player.seek(delta > 0 ? current.add(Duration.seconds(delta)) : current.subtract(Duration.seconds(delta)));
    });
  }

  @Override
  public BufferedImage snapshots() {
    return null;
//    final Media media = player.getMedia();
//    int width = media.getWidth();
//    int height = media.getHeight();
//    WritableImage wim = new WritableImage(width, height);
//    MediaView mv = new MediaView();
//    mv.setFitWidth(width);
//    mv.setFitHeight(height);
//    mv.setMediaPlayer(player);
//    mv.snapshot(null, wim);
//    try {
//      ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", new File("/test.png"));
//    } catch (Exception s) {
//      System.out.println(s);
//    }
//    return null;
  }

  @Override
  public void stop() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int volume() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setVolume(int volume) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void play(String uri) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPosition(float f) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public float position() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void toggleFullScreen() {
    // TODO Auto-generated method stub
    
  }
}
