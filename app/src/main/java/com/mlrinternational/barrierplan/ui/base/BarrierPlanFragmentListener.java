package com.mlrinternational.barrierplan.ui.base;

import android.support.v4.util.Pair;
import com.mlrinternational.barrierplan.data.BarrierType;

public interface BarrierPlanFragmentListener {
  Pair<Integer, Double> getCalculation(Double length, BarrierType currentSingleType);

  String getMetricString();
}
