package com.mlrinternational.barrierplan.ui.products;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mlrinternational.barrierplan.R;

public class ProductsImageGalleryAdapter extends RecyclerView.Adapter {

  private LayoutInflater inflater;
  private Context context;
  private int[] resources;

  public ProductsImageGalleryAdapter(final Context context) {
    this.context = context;
    inflater = LayoutInflater.from(context);
  }

  @Override public int getItemCount() {
    return resources.length;
  }

  @Override public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    if (holder instanceof ProductsGalleryViewHolder) {
      final ProductsGalleryViewHolder vh = (ProductsGalleryViewHolder) holder;
      vh.imageView.setImageDrawable(ContextCompat.getDrawable(
          context, resources[position])
      );
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(
      final ViewGroup parent,
      final int viewType) {
    return new ProductsGalleryViewHolder(
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_product_gallery, parent, false)
    );
  }

  public void setResources(final int[] resources) {
    this.resources = resources;
    notifyDataSetChanged();
  }

  class ProductsGalleryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imageView) ImageView imageView;

    public ProductsGalleryViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
