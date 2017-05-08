package com.mlrinternational.barrierplan.ui.landing;

import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.data.Metric;
import com.mlrinternational.barrierplan.ui.base.BasePresenter;
import com.mlrinternational.barrierplan.utils.NavigationFactory;
import com.mlrinternational.barrierplan.utils.UnitUtils;

import static com.mlrinternational.barrierplan.utils.NavigationFactory.*;

public class LandingPresenter extends BasePresenter<LandingView> {

  private final NavigationFactory navigationFactory = new NavigationFactory();
  private final Metric currentMetric = Metric.IMPERIAL;

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
    final Double remainder = lengthNeeded - (barriers * barrierLength);
    return Pair.create(barriers.intValue(), remainder);
  }

  public String getMetricString() {
    switch (currentMetric) {
      case IMPERIAL:
        return "\"";
      case METRIC:
        return "cm";
      default:
        return "\"";
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
        view.showProductS();
        break;
    }

  }
}
