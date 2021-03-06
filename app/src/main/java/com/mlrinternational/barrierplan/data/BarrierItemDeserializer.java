package com.mlrinternational.barrierplan.data;

import org.json.JSONException;
import org.json.JSONObject;

import static com.mlrinternational.barrierplan.data.BarrierType.MINIT;
import static com.mlrinternational.barrierplan.data.BarrierType.MOVIT;
import static com.mlrinternational.barrierplan.data.BarrierType.XTENDIT;
import static com.mlrinternational.barrierplan.data.CustomBarrier.LENGTH_IMPERIAL;
import static com.mlrinternational.barrierplan.data.CustomBarrier.TYPE;

public class BarrierItemDeserializer {

  public static BarrierItem getFromJson(final String input) throws JSONException {
    final JSONObject object = new JSONObject(input);
    final String type = object.getString(TYPE);
    if (MINIT.getType().equals(type)) {
      return MINIT;
    } else if (MOVIT.getType().equals(type)) {
      return MOVIT;
    } else if (XTENDIT.getType().equals(type)) {
      return XTENDIT;
    }
    else {
      return CustomBarrier.getFromJson(type, object.getString(LENGTH_IMPERIAL));
    }
  }
}
