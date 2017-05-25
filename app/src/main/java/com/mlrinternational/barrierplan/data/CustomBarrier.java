package com.mlrinternational.barrierplan.data;

import com.mlrinternational.barrierplan.utils.UnitUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomBarrier implements BarrierItem {

  public static final String TYPE = "type";
  public static final String LENGTH_IMPERIAL = "lengthImperial";
  public static final String LENGTH_METRIC = "lengthMetric";
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

  public static CustomBarrier getFromJson(final String name, final String length) {
    final Double value = Double.valueOf(length);
    return new CustomBarrier(
        name,
        UnitUtils.convertUp(value, Metric.IMPERIAL).toString(),
        Metric.IMPERIAL
    );
  }

  @Override public String getJsonString() throws JSONException {
    final JSONObject jsonObject = new JSONObject();
    jsonObject.put(TYPE, name);
    jsonObject.put(LENGTH_IMPERIAL, lengthImperial);
    jsonObject.put(LENGTH_METRIC, lengthMetric);
    return jsonObject.toString();
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
