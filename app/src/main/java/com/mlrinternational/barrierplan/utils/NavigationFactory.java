package com.mlrinternational.barrierplan.utils;

import android.view.MenuItem;
import com.mlrinternational.barrierplan.R;

public class NavigationFactory {

  public NavigationFactory(){
  }

  public NavigationItem getNavigationItem(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_calculate:
        return NavigationItem.CALCULATE;
      case R.id.action_events:
        return NavigationItem.EVENTS;
      case R.id.action_products:
        return NavigationItem.PRODUCTS;
      default:
        return NavigationItem.CALCULATE;
    }
  }

  public enum NavigationItem{
    CALCULATE, EVENTS, PRODUCTS
  }
}
