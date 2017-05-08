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
}
