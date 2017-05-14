package com.mlrinternational.barrierplan.data;

import com.mlrinternational.barrierplan.utils.UnitUtils;

public class CustomBarrier implements BarrierItem {

  private double lengthImperial;
  private double lengthMetric;
  private String name;

  public CustomBarrier(final String name, final String length, final Metric currentMetric) {
    this.name = name;
    final Double value = Double.valueOf(length);
    if (currentMetric == Metric.IMPERIAL) {
      lengthImperial = UnitUtils.convertDown(value, Metric.IMPERIAL);
      lengthMetric =
          UnitUtils.convertDown(UnitUtils.convertBetween(value, Metric.IMPERIAL), Metric.METRIC);
    } else {
      lengthMetric = UnitUtils.convertDown(value, Metric.METRIC);
      lengthImperial =
          UnitUtils.convertDown(UnitUtils.convertBetween(value, Metric.METRIC), Metric.IMPERIAL);
    }
  }

  public double getLengthImperial() {
    return lengthImperial;
  }

  public double getLengthMetric() {
    return lengthMetric;
  }

  public String getType() {
    return name;
  }
}
