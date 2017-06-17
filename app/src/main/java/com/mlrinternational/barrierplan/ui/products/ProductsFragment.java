package com.mlrinternational.barrierplan.ui.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.ui.base.BaseBarrierPlanFragment;
import com.mlrinternational.barrierplan.ui.products.ProductsSelectorAdapter.ChangeProductsListener;
import java.util.List;

public class ProductsFragment extends BaseBarrierPlanFragment implements
    ChangeProductsListener {

  static final ButterKnife.Setter<TextView, String[]> setText =
      (view, value, index) -> view.setText(value[index]);

  @BindView(R.id.description_title) TextView descriptionTitle;
  @BindViews({
                 R.id.length,
                 R.id.height,
                 R.id.weight,
                 R.id.resistance,
                 R.id.handling
             })
  List<TextView> descriptionViews;
  @BindView(R.id.pager) RecyclerView pager;
  @BindView(R.id.product_selector) RecyclerView productsSelector;
  @BindView(R.id.normal_colors) View normalColors;
  @BindView(R.id.orange_colors) View orangeColors;

  private ProductsSelectorAdapter selectorAdapter;
  private ProductsImageGalleryAdapter imageGalleryAdapter;

  private int[] movitResources = {
      R.drawable.movit1, R.drawable.movit2, R.drawable.movit3, R.drawable.movit4
  };
  private int[] minitResources = {
      R.drawable.minit1, R.drawable.minit2, R.drawable.minit3, R.drawable.minit4
  };
  private int[] xtenditResources = {
      R.drawable.rsz_xtendit
  };
  private int[] multiGateResources = {
      R.drawable.multi_gate
  };
  private int[][] resourceList = {
      movitResources, minitResources, xtenditResources, multiGateResources
  };
  private String[] movitDescription = {
      "6'6\" (78\")", "3'3\" (39\")", "25.5 lbs. (11.5 kg)",
      "+131F,-4F (+55C,-20C)", "Ships via LTL"
  };
  private String[] minitDescription = {
      "4'1\" (49\")", "3'3\" (39\")", "15.4 lbs", "+131F,-4F (+55C,-20C)", "Ships via LTL"
  };

  private String[] xtenditDescription = {
      "5'1\" (61\")", "10.4\"", "7.6 lbs (3.5kg)", "+131F,-4F (+55C,-20C)", "Ships via LTL"
  };

  private String[] multiGateDescription = {
      "5.35\" closed, 90\" (x ft) opened", "43\"", "15 lbs empty, 35 lbs filled with water",
      "+ 55 C – + 130 F", "Ships via LTL"
  };
  private String[][] descriptions = {
      movitDescription, minitDescription, xtenditDescription, multiGateDescription
  };

  private int[] titles = {
      R.string.movit_description, R.string.minit_description, R.string.xtendit_description,
      R.string.multi_gate_description
  };

  public static ProductsFragment getInstance() {
    return new ProductsFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_products;
  }

  @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    selectorAdapter = new ProductsSelectorAdapter(
        getActivity(),
        BarrierType.values(),
        this
    );
    imageGalleryAdapter = new ProductsImageGalleryAdapter(getActivity());
  }

  @Override public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    productsSelector.setLayoutManager(new LinearLayoutManager(
        getActivity(),
        LinearLayoutManager.HORIZONTAL,
        false
    ));
    productsSelector.setAdapter(selectorAdapter);
    pager.setLayoutManager(new LinearLayoutManager(
        getActivity(),
        LinearLayoutManager.HORIZONTAL,
        false
    ));
    pager.setAdapter(imageGalleryAdapter);
  }

  @Override public void onStart() {
    super.onStart();
    listener.showContactToolbar();
    selectorAdapter.setSelected(0);
    changeProduct(0);
  }

  @Override public void changeProduct(final int position) {
    imageGalleryAdapter.setResources(resourceList[position]);
    setDescriptionViews(descriptions[position]);
    descriptionTitle.setText(getString(titles[position]));
    normalColors.setVisibility(
        position == BarrierType.values().length - 1 ? View.GONE : View.VISIBLE);
    orangeColors.setVisibility(
        position == BarrierType.values().length - 1 ? View.VISIBLE : View.GONE);
    selectorAdapter.notifyDataSetChanged();
  }

  private void setDescriptionViews(final String[] currentDisplay) {
    ButterKnife.apply(descriptionViews, setText, currentDisplay);
  }
}
