package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.cutplayer4j.time.Time.formatTime;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.cutplayer4j.event.TickEvent;
import com.github.cutplayer4j.view.StandardLabel;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

final class PositionPane extends JPanel {

  private final JLabel timeLabel;

  private final JSlider positionSlider;

  private final JLabel durationLabel;

  private long time;

  private final AtomicBoolean sliderChanging = new AtomicBoolean();

  private final AtomicBoolean positionChanging = new AtomicBoolean();

  PositionPane() {
    timeLabel = new StandardLabel("9:99:99");

    UIManager.put("Slider.paintValue", false);
    positionSlider = new JSlider();
    positionSlider.setMinimum(0);
    positionSlider.setMaximum(Integer.MAX_VALUE);
    positionSlider.setValue(0);

    positionSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (!positionChanging.get()) {
          JSlider source = (JSlider) e.getSource();
          if (source.getValueIsAdjusting()) {
            sliderChanging.set(true);
          }
          else {
            sliderChanging.set(false);
          }
          application().mediaPlayer().setPosition(source.getValue());
        }
      }
    });

    durationLabel = new StandardLabel("9:99:99");

    setLayout(new MigLayout("fill, insets 0 0 0 0", "[][grow][]", "[]"));

    add(timeLabel, "shrink");
    add(positionSlider, "grow");
    add(durationLabel, "shrink");

    timeLabel.setText("-:--:--");
    durationLabel.setText("-:--:--");

    application().subscribe(this);
  }

  private void refresh() {
    timeLabel.setText(formatTime(time));

    if (!sliderChanging.get()) {
      int value = (int) (application().mediaPlayer().position());
      positionChanging.set(true);
      positionSlider.setValue(value);
      positionChanging.set(false);
    }
  }
  
  void setMaximum(long maximum) {
    this.positionSlider.setMaximum(maximum >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)maximum);
  }

  void setTime(long time) {
    this.time = time;
  }

  void setDuration(long duration) {
    durationLabel.setText(formatTime(duration));
  }

  @Subscribe
  public void onTick(TickEvent tick) {
    setTime(application().mediaPlayer().position());
    setDuration(application().mediaPlayer().duration());
    refresh();
  }
}
