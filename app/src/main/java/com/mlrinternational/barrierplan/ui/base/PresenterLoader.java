package com.mlrinternational.barrierplan.ui.base;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

public class PresenterLoader<P extends BasePresenter> extends Loader<P> {
  private final String tag;
  private P presenter;

  public PresenterLoader(final Context context, final P presenter, final String tag) {
    super(context);
    this.presenter = presenter;
    this.tag = tag;
  }

  @Override
  public void deliverResult(final P data) {
    super.deliverResult(data);
    Log.i(tag, "%s - loader deliverResult");
  }

  @Override
  protected void onForceLoad() {
    Log.i(tag, "%s - loader onForceLoad");

    // Deliver the result
    deliverResult(presenter);
  }

  @Override
  protected void onReset() {
    Log.i(tag, "%s - loader onReset");
    if (presenter != null) {
      presenter.onDestroy();
      presenter = null;
    }
  }

  @Override
  protected void onStartLoading() {
    Log.i(tag, "%s - loader onStartLoading");

    // if we already own a presenter instance, simply deliver it.
    if (presenter != null) {
      Log.i(tag, "%s - loader deliverResult in onStartLoading");
      deliverResult(presenter);
      return;
    }

    // Otherwise, force a load
    forceLoad();
  }

  @Override
  protected void onStopLoading() {
    Log.i(tag, "%s - loader onStopLoading");
  }
}
