package com.mlrinternational.barrierplan.ui.calculate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.ui.base.BaseBarrierPlanFragment;

public class CalculateFragment extends BaseBarrierPlanFragment {

  @BindView(R.id.single_barrier_container) CardView singleBarrierContainer;
  @BindView(R.id.multi_barrier_container) CardView multiBarrierContainer;

  public static CalculateFragment newInstance() {
   return new CalculateFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_calculate;
  }

  @Nullable @Override public View onCreateView(
      final LayoutInflater inflater,
      @Nullable final ViewGroup container,
      @Nullable final Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
