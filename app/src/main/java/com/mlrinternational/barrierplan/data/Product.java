package com.mlrinternational.barrierplan.data;

public class Product {

  private String[] descriptions;
  private BarrierType barrierType;

  public Product(final String[] descriptions, final BarrierType barrierType) {
    this.descriptions = descriptions;
    this.barrierType = barrierType;
  }

  public String[] getDescriptions() {
    return descriptions;
  }

  public BarrierType getBarrierType() {
    return barrierType;
  }
}
