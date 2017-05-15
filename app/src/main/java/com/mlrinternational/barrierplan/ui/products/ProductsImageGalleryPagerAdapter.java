package com.mlrinternational.barrierplan.ui.products;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.mlrinternational.barrierplan.R;

public class ProductsImageGalleryPagerAdapter extends PagerAdapter {

  private LayoutInflater inflater;
  private Context context;
  private int[] resources;

  public ProductsImageGalleryPagerAdapter(final Context context) {
    this.context = context;
    inflater = LayoutInflater.from(context);
  }

  @Override public void destroyItem(
      final ViewGroup container,
      final int position,
      final Object object) {
    container.removeView((LinearLayout) object);
  }

  @Override public int getCount() {
    return resources.length;
  }

  @Override public Object instantiateItem(final ViewGroup container, final int position) {
    final View itemView = inflater.inflate(R.layout.item_product_gallery, container, false);
    ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
    imageView.setImageResource(resources[position]);

    container.addView(itemView);

    return itemView;
  }

  @Override public boolean isViewFromObject(final View view, final Object object) {
    return view == object;
  }

  public void setResources(final int[] resources) {
    this.resources = resources;
  }
}
