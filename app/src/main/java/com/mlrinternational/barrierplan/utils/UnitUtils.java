package com.mlrinternational.barrierplan.utils;

import com.mlrinternational.barrierplan.data.Metric;

public class UnitUtils {

  public static Double convertDown(final Double amount, final Metric metric) {
    final Double result;
    switch (metric) {
      case IMPERIAL:
        result = amount * 12;
        break;
      case METRIC:
        result =  amount * 100;
        break;
      default:
        result =  amount * 12;
        break;
    }
    return result;
  }

  public static Double convertBetween(final Double amount, final Metric currentMetric) {
    final Double result;
    switch (currentMetric) {
      case IMPERIAL:
        result = amount * .3048;
        break;
      case METRIC:
        result =amount * 3.28;
        break;
      default:
        result = amount * .3048;
        break;
    }
    return result;
  }

  public static Double convertUp(final Double amount, final Metric currentMetric) {
    final Double result;
    switch (currentMetric) {
      case IMPERIAL:
        result = amount / 12;
        break;
      case METRIC:
        result = amount / 100;
        break;
      default:
        result = amount / 12;
        break;
    }
    return result;
  }
}
