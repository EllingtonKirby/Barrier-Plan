package com.mlrinternational.barrierplan.ui.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.ui.base.BarrierPlanFragmentListener;
import com.mlrinternational.barrierplan.ui.base.BaseActivity;
import com.mlrinternational.barrierplan.ui.calculate.CalculateFragment;
import com.mlrinternational.barrierplan.utils.NavigationFactory;

public class LandingActivity extends BaseActivity<LandingPresenter, LandingView>
    implements LandingView, BarrierPlanFragmentListener {

  @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
  @BindView(R.id.container) FrameLayout container;

  private final NavigationFactory navigationFactory = new NavigationFactory();
  private final BottomNavigationView.OnNavigationItemReselectedListener navListener =
      item -> presenter.onNavigationItemSelected(navigationFactory.getNavigationItem(item));
  private FragmentManager fragmentManager;

  @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentManager = getSupportFragmentManager();
    //Default
    showCalculate();
  }

  public static void start(final Context context) {
    context.startActivity(new Intent(context, LandingActivity.class));
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override public int getLayoutId() {
    return R.layout.activity_landing;
  }

  @Override public LandingPresenter getPresenter() {
    return new LandingPresenter();
  }

  @Override public void showCalculate() {
    replaceFragment(CalculateFragment.getInstance());
  }

  @Override public void showEvents() {

  }

  @Override public void showProductS() {

  }

  @Override public Pair<Integer, Double> getCalculation(
      final Double lengthNeeded, final BarrierType currentSingleType) {
    return presenter.getCalculation(lengthNeeded, currentSingleType);
  }

  @Override public String getMetricString() {
    return presenter.getMetricString();
  }

  private void replaceFragment(final Fragment fragment) {
    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
  }
}
