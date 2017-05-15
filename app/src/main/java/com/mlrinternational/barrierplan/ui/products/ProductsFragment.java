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
import java.util.ArrayList;
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

  private ProductsImageGalleryPagerAdapter adapter;
  private BarrierType currentDisplay = BarrierType.MINIT;
  private Disposable btnMinitDisposable;
  private Disposable btnMovitDisposable;

  public static ProductsFragment getInstance() {
    return new ProductsFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_products;
  }

  @Override public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    adapter = new ProductsImageGalleryPagerAdapter(getContext());
    pager.setAdapter(adapter);
  }

  @Override public void onStart() {
    super.onStart();
    observeViews();
    changeDisplayedBarrierType();
  }

  private void changeDisplayedBarrierType() {
    switch (currentDisplay) {
      case MOVIT:
        currentDisplay = BarrierType.MINIT;
        btnMinit.setBackground(getResources().getDrawable(R.color.gray));
        btnMovit.setBackground(getResources().getDrawable(R.color.colorWhite));
        break;
      case MINIT:
        currentDisplay = BarrierType.MOVIT;
        btnMinit.setBackground(getResources().getDrawable(R.color.colorWhite));
        btnMovit.setBackground(getResources().getDrawable(R.color.gray));
        break;
    }
    //TODO set Pager adapter resources here
    setDescriptionViews(currentDisplay);
  }

  private List<String> getDescriptionTextFactory(final BarrierType currentDisplay) {
    final List<String> values = new ArrayList<>();
    if (currentDisplay == BarrierType.MOVIT) {

    } else {

    }
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
