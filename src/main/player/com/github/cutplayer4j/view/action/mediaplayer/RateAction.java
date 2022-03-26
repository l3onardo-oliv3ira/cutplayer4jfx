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


package com.github.cutplayer4j.view.action.mediaplayer;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;

import java.awt.event.ActionEvent;

import com.github.cutplayer4j.event.RateEvent;
import com.github.utils4j.gui.IResourceAction;
import com.google.common.eventbus.Subscribe;


final class RateAction extends MediaPlayerAction {

  private final double rate;

  RateAction(double rate, IResourceAction resource) {
    super(resource);
    this.rate = rate;
    select(this.rate == 1);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    application().mediaPlayer().setRate(rate);
    application().post(new RateEvent(rate));
  }
  
  @Subscribe
  public void onRate(RateEvent event) {
    super.select(this.rate == event.getRate());
  }
}
