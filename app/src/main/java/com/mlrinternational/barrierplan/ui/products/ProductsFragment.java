package com.mlrinternational.barrierplan.ui.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.view.RxView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.ui.base.BaseBarrierPlanFragment;
import io.reactivex.disposables.Disposable;
import java.util.Arrays;
import java.util.List;

public class ProductsFragment extends BaseBarrierPlanFragment {

  static final ButterKnife.Setter<TextView, List<String>> setText =
      (view, value, index) -> view.setText(value.get(index));

  @BindView(R.id.btn_movit) View btnMovit;
  @BindView(R.id.btn_minit) View btnMinit;
  @BindView(R.id.description_title) TextView descriptionTitle;
  @BindViews({
                 R.id.length,
                 R.id.height,
                 R.id.weight,
                 R.id.load,
                 R.id.resistance,
                 R.id.handling
             })
  List<TextView> descriptionViews;
  @BindView(R.id.pager) ViewPager pager;

  private ProductsImageGalleryPagerAdapter movitAdapter;
  private ProductsImageGalleryPagerAdapter minitAdapter;
  private BarrierType currentDisplay = BarrierType.MINIT;
  private Disposable btnMinitDisposable;
  private Disposable btnMovitDisposable;
  private int[] movitResources = {
      R.drawable.movit1, R.drawable.movit2, R.drawable.movit3, R.drawable.movit4
  };
  private int[] minitResources = {
      R.drawable.minit1, R.drawable.minit2, R.drawable.minit3, R.drawable.minit4
  };

  public static ProductsFragment getInstance() {
    return new ProductsFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_products;
  }

  @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    movitAdapter = new ProductsImageGalleryPagerAdapter(getContext());
    minitAdapter = new ProductsImageGalleryPagerAdapter(getContext());
  }

  @Override public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    movitAdapter.setResources(movitResources);
    minitAdapter.setResources(minitResources);
    pager.setOffscreenPageLimit(3);
  }

  @Override public void onStart() {
    super.onStart();
    observeViews();
    listener.showContactToolbar();
    changeDisplayedBarrierType();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    btnMinitDisposable.dispose();
    btnMovitDisposable.dispose();
    btnMovitDisposable = null;
    btnMinitDisposable = null;
  }

  private void changeDisplayedBarrierType() {
    switch (currentDisplay) {
      case MOVIT:
        currentDisplay = BarrierType.MINIT;
        btnMinit.setBackground(getResources().getDrawable(R.color.gray));
        btnMovit.setBackground(getResources().getDrawable(R.color.colorWhite));
        descriptionTitle.setText(getString(R.string.movit_description));
        pager.setAdapter(minitAdapter);
        break;
      case MINIT:
        currentDisplay = BarrierType.MOVIT;
        btnMinit.setBackground(getResources().getDrawable(R.color.colorWhite));
        btnMovit.setBackground(getResources().getDrawable(R.color.gray));
        descriptionTitle.setText(getString(R.string.minit_description));
        pager.setAdapter(movitAdapter);
        break;
    }

    setDescriptionViews(currentDisplay);
  }

  private List<String> getDescriptionTextFactory(final BarrierType currentDisplay) {
    final List<String> values;
    final String[] strings;
    if (currentDisplay == BarrierType.MOVIT) {
      strings = new String[] {
          "6'6\" (78\")", "3'3\" (39\")", "25.5 lbs. (11.5 kg)", "1102 lbs. (500 kg)",
          "+131F,-4F (+55C,-20C)", "Ships via LTL"
      };
    } else {
      strings = new String[] {
          "4'1\" (49\")", "3'3\" (39\")", "15.4 lbs", "", "+131F,-4F (+55C,-20C)", "Ships via LTL"
      };
    }
    values = Arrays.asList(strings);
    return values;
  }

  private void observeViews() {
    btnMinitDisposable = RxView.clicks(btnMinit)
        .filter(o -> currentDisplay != BarrierType.MINIT)
        .subscribe(
            o -> changeDisplayedBarrierType()
        );

    btnMovitDisposable = RxView.clicks(btnMovit)
        .filter(o -> currentDisplay != BarrierType.MOVIT)
        .subscribe(
            o -> changeDisplayedBarrierType()
        );
  }

  private void setDescriptionViews(final BarrierType currentDisplay) {
    ButterKnife.apply(descriptionViews, setText, getDescriptionTextFactory(currentDisplay));
  }
}
