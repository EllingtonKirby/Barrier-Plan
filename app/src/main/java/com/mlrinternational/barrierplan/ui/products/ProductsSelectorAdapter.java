package com.mlrinternational.barrierplan.ui.products;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.view.RxView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductsSelectorAdapter extends RecyclerView.Adapter {

  private final BarrierType[] items;
  private List<Boolean> selected = new ArrayList<>();
  private ChangeProductsListener listener;
  private Context context;

  public ProductsSelectorAdapter(
      final Context context,
      final BarrierType[] items,
      final ChangeProductsListener listener) {
    this.context = context;
    this.items = items;
    this.listener = listener;
    for (final BarrierType ignored : items) {
      selected.add(false);
    }
  }

  @Override public int getItemCount() {
    return items.length;
  }

  @Override public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    final BarrierType item = items[position];
    if (holder instanceof ProductsSelectorViewHolder) {
      final ProductsSelectorViewHolder vh = (ProductsSelectorViewHolder) holder;
      vh.imageView.setImageDrawable(
          ContextCompat.getDrawable(context, item.getLogo())
      );
      if (selected.get(position)) {
        vh.cardView.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_logo));
      } else {
        vh.cardView.setBackground(ContextCompat.getDrawable(context, R.drawable.not_selected));
      }
      RxView.clicks(vh.itemView)
          .throttleFirst(1, TimeUnit.SECONDS)
          .subscribe(v -> {
                for (int i = 0; i < getItemCount(); i++) {
                  selected.set(i, i == position);
                }
                listener.changeProduct(position);
              }
          );
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(
      final ViewGroup parent,
      final int viewType) {
    return new ProductsSelectorViewHolder(
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_product_selector, parent, false)
    );
  }

  public void setSelected(final int position) {
    for (int i = 0; i < getItemCount(); i++) {
      selected.set(i, i == position);
    }
  }

  public interface ChangeProductsListener {
    void changeProduct(int position);
  }

  class ProductsSelectorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.card) CardView cardView;

    public ProductsSelectorViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
