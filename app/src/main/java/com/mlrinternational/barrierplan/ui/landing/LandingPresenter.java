package com.mlrinternational.barrierplan.ui.landing;

import android.support.v4.util.Pair;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.data.Metric;
import com.mlrinternational.barrierplan.ui.base.BasePresenter;
import com.mlrinternational.barrierplan.utils.NavigationFactory;
import com.mlrinternational.barrierplan.utils.UnitUtils;

import static com.mlrinternational.barrierplan.utils.NavigationFactory.NavigationItem;

public class LandingPresenter extends BasePresenter<LandingView> {

  public static final String IMPERIAL_STRING = "\"";
  public static final String METRIC_STRING = "cm";

  private final NavigationFactory navigationFactory = new NavigationFactory();
  private Metric currentMetric = Metric.IMPERIAL;

  @Override public void onDestroy() {

  }

  public Pair<Integer, Double> getCalculation(
      final Double input,
      final BarrierType currentSingleType) {
    final double barrierLength;
    final double lengthNeeded = UnitUtils.convertDown(input, currentMetric);
    switch (currentMetric) {
      case IMPERIAL:
        barrierLength = currentSingleType.getLengthImperial();
        break;
      case METRIC:
        barrierLength = currentSingleType.getLengthMetric();
        break;
      default:
        barrierLength = currentSingleType.getLengthImperial();
        break;
    }
    final Double barriers = Math.floor(lengthNeeded / barrierLength);
    Double remainder = (lengthNeeded - (barriers * barrierLength));
    if (currentMetric == Metric.METRIC) {
      remainder = Math.ceil(remainder);
    }
    return Pair.create(barriers.intValue(), remainder);
  }

  public String getMetricString() {
    switch (currentMetric) {
      case IMPERIAL:
        return IMPERIAL_STRING;
      case METRIC:
        return METRIC_STRING;
      default:
        return IMPERIAL_STRING;
    }
  }

  public void onNavigationItemSelected(final NavigationItem navigationItem) {
    switch (navigationItem) {
      case CALCULATE:
        view.showCalculate();
        break;
      case EVENTS:
        view.showEvents();
        break;
      case PRODUCTS:
        view.showProducts();
        break;
    }
  }

  public void onUnitsChanged() {
    switch (currentMetric) {
      case IMPERIAL:
        currentMetric = Metric.METRIC;
        break;
      case METRIC:
        currentMetric = Metric.IMPERIAL;
    }
  }
}
