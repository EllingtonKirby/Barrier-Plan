package com.mlrinternational.barrierplan.data;

public enum  BarrierType implements BarrierItem {

  MOVIT("movit",  191.77, 75.5),
  MINIT("minit",  118.11, 46.5);

  private String type;
  private double lengthMetric;
  private double lengthImperial;

  BarrierType(final String type, final double lengthMetric, final double lengthImperial) {
    this.type = type;
    this.lengthMetric = lengthMetric;
    this.lengthImperial = lengthImperial;
  }

  public double getLengthImperial() {
    return lengthImperial;
  }

  public double getLengthMetric() {
    return lengthMetric;
  }

  @Override public String getType() {
    return type;
  }
}
