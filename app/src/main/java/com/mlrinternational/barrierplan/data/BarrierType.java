package com.mlrinternational.barrierplan.data;

import android.support.annotation.DrawableRes;
import com.mlrinternational.barrierplan.R;

public enum BarrierType implements BarrierItem {

  MOVIT("movit", 191.77, 75.5, R.drawable.movit_logo),
  MINIT("minit", 118.11, 46.5, R.drawable.minit_logo);

  private String type;
  private double lengthMetric;
  private double lengthImperial;
  private @DrawableRes int logo;

  BarrierType(
      final String type,
      final double lengthMetric,
      final double lengthImperial,
      final @DrawableRes int logo) {
    this.type = type;
    this.lengthMetric = lengthMetric;
    this.lengthImperial = lengthImperial;
    this.logo = logo;
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

  public int getLogo() {
    return logo;
  }
}
