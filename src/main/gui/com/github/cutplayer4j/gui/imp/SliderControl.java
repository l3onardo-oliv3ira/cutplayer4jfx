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


package com.github.cutplayer4j.gui.imp;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderControl extends JPanel implements ChangeListener {

  private final float factor = 100f;
  private final String valueFormat;

  private final JLabel label;
  private final JSlider slider;
  private final JLabel valueLabel;

  public SliderControl(String text, float min, float max, float value, String valueFormat) {
    this.valueFormat = valueFormat;

    label = new SmallStandardLabel();
    label.setText(text);
    label.setHorizontalAlignment(JLabel.CENTER);

    int modelMin = (int)(min * factor);
    int modelMax = (int)(max * factor);
    int modelValue = (int)(value * factor);

    modelValue = Math.min(modelValue, modelMax);
    modelValue = Math.max(modelValue, modelMin);

    slider = new JSlider(JSlider.VERTICAL, modelMin, modelMax, modelValue);
    valueLabel = new SmallStandardLabel();

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    slider.setAlignmentX(JSlider.CENTER_ALIGNMENT);
    valueLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

    add(slider);
    add(label);
    add(valueLabel);

    slider.getModel().addChangeListener(this);

    updateValue();
  }

  public final JSlider getSlider() {
    return slider;
  }

  @Override
  public final void setEnabled(boolean enabled) {
    slider.setEnabled(enabled);
  }

  @Override
  public final void stateChanged(ChangeEvent e) {
    updateValue();
  }

  private void updateValue() {
    int value = slider.getValue();
    valueLabel.setText(String.format(valueFormat, value / factor));
  }
}
