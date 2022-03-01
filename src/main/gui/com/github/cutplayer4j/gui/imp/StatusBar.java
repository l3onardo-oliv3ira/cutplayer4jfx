package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.cutplayer4j.time.Time.formatTime;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.cutplayer4j.event.TickEvent;
import com.github.cutplayer4j.view.BorderedStandardLabel;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

final class StatusBar extends JPanel {

  private final JLabel titleLabel;

  private final JLabel rateLabel;

  private final JLabel timeLabel;

  private long time;

  private long duration;

  StatusBar() {
    titleLabel = new BorderedStandardLabel();
    rateLabel = new BorderedStandardLabel();
    timeLabel = new BorderedStandardLabel();

    setLayout(new MigLayout("fillx, insets 2 n n n", "[grow]16[][]", "[]"));
    add(titleLabel, "grow");
    add(rateLabel);
    add(timeLabel);

    application().subscribe(this);
  }

  void setTitle(String title) {
    titleLabel.setText(title);
  }

  void setRate(String rate) {
    rateLabel.setText(rate);
  }

  void setTime(long time) {
    this.time = time;
  }

  void setDuration(long duration) {
    this.duration = duration;
    refresh();
  }

  void clear() {
    titleLabel.setText(null);
    rateLabel.setText(null);
    timeLabel.setText(null);
  }

  void refresh() {
    timeLabel.setText(String.format("%s/%s", formatTime(time), formatTime(duration)));
  }

  @Subscribe
  public void onTick(TickEvent tick) {
    setTime(application().mediaPlayer().position());
    refresh();
  }
}
