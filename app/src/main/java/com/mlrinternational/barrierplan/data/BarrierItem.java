package com.mlrinternational.barrierplan.data;

import org.json.JSONException;

public interface BarrierItem {
  double getLengthImperial();

  double getLengthMetric();

  String getType();

  String getJsonString() throws JSONException;
}
