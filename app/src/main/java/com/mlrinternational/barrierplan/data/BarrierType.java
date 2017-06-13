package com.mlrinternational.barrierplan.data;

import android.support.annotation.DrawableRes;
import com.mlrinternational.barrierplan.R;
import org.json.JSONException;
import org.json.JSONObject;

import static com.mlrinternational.barrierplan.data.CustomBarrier.TYPE;

public enum BarrierType implements BarrierItem {

  MOVIT("Movit", 191.77, 75.5, R.drawable.movit_logo),
  MINIT("Minit", 118.11, 46.5, R.drawable.minit_logo),
  XTENDIT("Xtendit", 154.94, 61,  R.drawable.xtendit_logo);

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

  public String getJsonString() throws JSONException {
    final JSONObject jsonObject = new JSONObject();
    jsonObject.put(TYPE, type);
    return jsonObject.toString();
  }
}
