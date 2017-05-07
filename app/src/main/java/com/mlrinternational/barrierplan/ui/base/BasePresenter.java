package com.mlrinternational.barrierplan.ui.base;

import android.util.Log;
import com.mlrinternational.barrierplan.utils.ErrorHandler;

public abstract class BasePresenter<T extends BaseView> implements ErrorHandler {

  protected T view;
  private boolean viewAttached;

  public abstract void onDestroy();

  public void bindView(final T view) {
    if (!viewAttached) {
      this.view = view;
      viewAttached = true;
    }
  }

  @Override public void logError(final String error) {
    Log.e(getClass().getSimpleName(), error);
  }
}
