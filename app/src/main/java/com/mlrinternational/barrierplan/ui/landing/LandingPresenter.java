package com.mlrinternational.barrierplan.ui.landing;

import android.support.v4.app.Fragment;
import com.mlrinternational.barrierplan.ui.base.BasePresenter;
import com.mlrinternational.barrierplan.utils.NavigationFactory;

import static com.mlrinternational.barrierplan.utils.NavigationFactory.*;

public class LandingPresenter extends BasePresenter<LandingView> {

  private final NavigationFactory navigationFactory = new NavigationFactory();

  @Override public void onDestroy() {

  }

  public void onNavigationItemSelected(final NavigationItem navigationItem) {
    //final Fragment pendingFragment =
  }
}
