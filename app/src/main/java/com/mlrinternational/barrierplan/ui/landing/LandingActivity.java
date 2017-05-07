package com.mlrinternational.barrierplan.ui.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import butterknife.BindView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.ui.base.BaseActivity;
import com.mlrinternational.barrierplan.utils.NavigationFactory;

public class LandingActivity extends BaseActivity<LandingPresenter, LandingView>
    implements LandingView {

  @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;

  private final NavigationFactory navigationFactory = new NavigationFactory();
  private final BottomNavigationView.OnNavigationItemReselectedListener navListener =
      item -> presenter.onNavigationItemSelected(navigationFactory.getNavigationItem(item));

  private FragmentManager fragmentManager;

  @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentManager = getSupportFragmentManager();
    //Default
    presenter.onNavigationItemSelected(NavigationFactory.NavigationItem.CALCULATE);
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
}
