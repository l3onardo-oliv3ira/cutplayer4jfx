package com.github.cutplayer4j.event;

public class RateEvent {
  private final double rate;
  
  public RateEvent(double rate) {
    this.rate = rate;
  }
  
  public double getRate() {
    return rate;
  }
}
