package com.mlrinternational.barrierplan.ui.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierItem;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.ui.base.BarrierPlanFragmentListener;
import com.mlrinternational.barrierplan.ui.base.BaseActivity;
import com.mlrinternational.barrierplan.ui.calculate.CalculateFragment;
import com.mlrinternational.barrierplan.utils.NavigationFactory;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class LandingActivity extends BaseActivity<LandingPresenter, LandingView>
    implements LandingView, BarrierPlanFragmentListener {

  private final NavigationFactory navigationFactory = new NavigationFactory();
  private final BottomNavigationView.OnNavigationItemReselectedListener navListener =
      item -> presenter.onNavigationItemSelected(navigationFactory.getNavigationItem(item));
  public Subject<String> unitChanged = PublishSubject.create();
  @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
  @BindView(R.id.container) FrameLayout container;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.btn_metric) TextView btnMetric;
  private AlertDialog unitsPicker;
  private FragmentManager fragmentManager;

  public static void start(final Context context) {
    context.startActivity(new Intent(context, LandingActivity.class));
  }

  @Override public int getLayoutId() {
    return R.layout.activity_landing;
  }

  @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentManager = getSupportFragmentManager();
    setUpUnitsDialog();
    //Default
    showCalculate();
  }

  @Override protected void onResume() {
    super.onResume();
    observeViews();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unitChanged = null;
  }

  @Override public Pair<Integer, Double> getCalculation(
      final Double lengthNeeded, final BarrierItem currentSingleType) {
    return presenter.getCalculation(lengthNeeded, currentSingleType);
  }

  @Override public Subject<String> getMetricChangedObservable() {
    return unitChanged;
  }

  @Override public String getMetricString() {
    return presenter.getMetricString();
  }

  @Override public LandingPresenter getPresenter() {
    return new LandingPresenter();
  }

  @Override public void showCalculate() {
    replaceFragment(CalculateFragment.getInstance());
  }

  @Override public void showEvents() {

  }

  @Override public void showProducts() {

  }

  private void observeViews() {
    RxView.clicks(btnMetric)
        .subscribe(
            o -> unitsPicker.show()
        );
  }

  private void replaceFragment(final Fragment fragment) {
    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
  }

  private void setUpUnitsDialog() {
    // 1. Instantiate an AlertDialog.Builder with its constructor
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    // 2. Chain together various setter methods to set the dialog characteristics
    builder.setTitle(R.string.choose_units);
    // Add the buttons
    builder.setNeutralButton(R.string.feet, (dialog, id) -> {
      presenter.onUnitsChanged();
      unitChanged.onNext(presenter.getMetricString());
      dialog.dismiss();
    });
    builder.setNegativeButton(R.string.meters, (dialog, id) -> {
      presenter.onUnitsChanged();
      unitChanged.onNext(presenter.getMetricString());
      dialog.dismiss();
    });
    unitsPicker = builder.create();
  }
}
