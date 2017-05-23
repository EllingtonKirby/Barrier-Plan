package com.mlrinternational.barrierplan.data;

import android.util.Pair;
import java.util.Date;
import java.util.List;

public class Event {

  private List<Pair<BarrierItem, Integer>> itemsByAmount;
  private String name;
  private Date date;
  public Event(
      final List<Pair<BarrierItem, Integer>> itemsByAmount,
      final String name,
      final Date date) {
    this.itemsByAmount = itemsByAmount;
    this.name = name;
    this.date = date;
  }

  public Date getDate() {
    return date;
  }

  public List<Pair<BarrierItem, Integer>> getItemsByAmount() {
    return itemsByAmount;
  }

  public void setItemsByAmount(final List<Pair<BarrierItem, Integer>> itemsByAmount) {
    this.itemsByAmount = itemsByAmount;
  }

  public String getName() {
    return name;
  }
}
