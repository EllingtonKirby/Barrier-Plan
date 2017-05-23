package com.mlrinternational.barrierplan.ui.base;

import android.util.Pair;
import com.mlrinternational.barrierplan.data.BarrierItem;
import com.mlrinternational.barrierplan.data.EventList;
import io.reactivex.subjects.Subject;
import java.util.Date;
import java.util.List;

public interface BarrierPlanFragmentListener {
  Pair<Integer, Double> getCalculation(Double length, BarrierItem currentSingleType);

  String getMetricString();

  Subject<String> getMetricChangedObservable();

  void showContactToolbar();

  void showMetricToolbar();

  void saveEvent(final String name, final Date date, final List<Pair<BarrierItem, Integer>> items);

  EventList getSavedEvents();

  boolean removeEvent(int position);

  void showSnackBar(String message);
}
