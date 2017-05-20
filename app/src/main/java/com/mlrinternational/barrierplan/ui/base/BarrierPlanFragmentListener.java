package com.mlrinternational.barrierplan.ui.base;

import android.support.v4.util.Pair;
import com.mlrinternational.barrierplan.data.BarrierItem;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.data.Metric;
import io.reactivex.subjects.Subject;

public interface BarrierPlanFragmentListener {
  Pair<Integer, Double> getCalculation(Double length, BarrierItem currentSingleType);

  String getMetricString();

  Subject<String> getMetricChangedObservable();

  void showContactToolbar();

  void showMetricToolbar();
}
