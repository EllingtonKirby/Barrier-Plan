package com.mlrinternational.barrierplan.ui.base;

import android.support.v4.util.Pair;
import com.mlrinternational.barrierplan.data.BarrierType;
import io.reactivex.subjects.Subject;

public interface BarrierPlanFragmentListener {
  Pair<Integer, Double> getCalculation(Double length, BarrierType currentSingleType);

  String getMetricString();

  Subject<String> getMetricChangedObservable();
}
