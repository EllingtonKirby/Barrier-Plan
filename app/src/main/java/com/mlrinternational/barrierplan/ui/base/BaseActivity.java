package com.mlrinternational.barrierplan.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter, V extends BaseView>
    extends AppCompatActivity {

  protected P presenter;

  @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
    if (presenter == null) {
      presenter = getPresenter();
    }
    super.onCreate(savedInstanceState);
    initLoader();
    setContentView(getLayoutId());
    ButterKnife.bind(this);
  }

  @Override protected void onStart() {
    super.onStart();
    presenter.bindView(getPresenterViewType());
  }



  @Override protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  public void showSnackbar(final View view, final String message) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
  }

  public V getPresenterViewType() {
    return (V) this;
  }

  public abstract @LayoutRes int getLayoutId();

  /**
   * Presenter Loader Pattern
   * Loaders are preserved between configuration changes, so storing presenter in a loader ensures
   * that business state can be retained independent of onSavedInstanceState
   */
  private void initLoader() {
    getSupportLoaderManager().initLoader(tag().hashCode(), null,
        new LoaderManager.LoaderCallbacks<P>() {
          @Override
          public Loader<P> onCreateLoader(final int id, final Bundle args) {
            Log.i(tag(), "%s - onCreateLoader callback");
            return new PresenterLoader<>(BaseActivity.this, presenter, tag());
          }

          @Override
          public void onLoadFinished(final Loader<P> loader, final P presenter) {
            Log.i(tag(), "%s - onLoadFinished callback");
            BaseActivity.this.presenter = presenter;
          }

          @Override
          public void onLoaderReset(final Loader<P> loader) {
            Log.i(tag(), "%s - onLoaderReset callback");
            BaseActivity.this.presenter = null;
          }
        }
    );
  }

  private String tag() {
    return getClass().getCanonicalName();
  }

  public abstract P getPresenter();
}
