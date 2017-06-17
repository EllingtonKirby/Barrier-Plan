package com.mlrinternational.barrierplan.utils;

import com.mlrinternational.barrierplan.data.Metric;
import javax.measure.Measure;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Length;
import static javax.measure.unit.NonSI.*;
import static javax.measure.unit.SI.*;
public class UnitUtils {

  public static Double convertBetween(final Double amount, final Metric currentMetric) {
    final Double result;
    switch (currentMetric) {
      case IMPERIAL:
        UnitConverter toMetric = FOOT.getConverterTo(METER);
        result = toMetric.convert(amount);
        break;
      case METRIC:
        UnitConverter toImperial = METER.getConverterTo(FOOT);
        result = toImperial.convert(amount);
        break;
      default:
        UnitConverter toMetricDefault = FOOT.getConverterTo(METER);
        result = toMetricDefault.convert(amount);
        break;
    }
    return result;
  }

  public static Double convertDown(final Double amount, final Metric metric) {
    final Double result;
    switch (metric) {
      case IMPERIAL:
        UnitConverter toFeet = FOOT.getConverterTo(INCH);
        result = toFeet.convert(amount);
        break;
      case METRIC:
        UnitConverter toMeters = METER.getConverterTo(CENTIMETER);
        result = toMeters.convert(amount);
        break;
      default:
        UnitConverter toFeetDefault = FOOT.getConverterTo(INCH);
        result = toFeetDefault.convert(amount);
        break;
    }
    return result;
  }

  public static Double convertUp(final Double amount, final Metric currentMetric) {
    final Double result;
    switch (currentMetric) {
      case IMPERIAL:
        UnitConverter toFeet = INCH.getConverterTo(FOOT);
        result = toFeet.convert(amount);
        break;
      case METRIC:
        UnitConverter toMeters = CENTIMETER.getConverterTo(METER);
        result = toMeters.convert(amount);
        break;
      default:
        UnitConverter toFeetDefault = INCH.getConverterTo(FOOT);
        result = toFeetDefault.convert(amount);
        break;
    }
    return result;
  }
}
