package com.mlrinternational.barrierplan.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public abstract class BaseBarrierPlanFragment extends Fragment {

  private BarrierPlanFragmentListener listener;

  public abstract @LayoutRes int getLayoutId();

  @Nullable @Override public View onCreateView(
      final LayoutInflater inflater,
      @Nullable final ViewGroup container,
      @Nullable final Bundle savedInstanceState) {
    final View view = inflater.inflate(getLayoutId(), container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onAttach(final Context context) {
    super.onAttach(context);
    if (context instanceof BarrierPlanFragmentListener) {
      listener = (BarrierPlanFragmentListener) context;
    } else {
      Log.e(getClass().getSimpleName(), "Unable to attach Listener");
    }
  }
}
