package com.mlrinternational.barrierplan.ui.calculate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.mlrinternational.barrierplan.data.BarrierItem;
import java.util.ArrayList;
import java.util.List;

public class MultipleBarrierTypeAdapter
    extends RecyclerView.Adapter<MultipleBarrierTypeAdapter.BarrierTypeViewHolder> {

  final List<BarrierItem> items = new ArrayList<>();

  @Override public int getItemCount() {
    return items.size();
  }

  @Override public void onBindViewHolder(
      final BarrierTypeViewHolder holder,
      final int position) {

  }

  @Override public BarrierTypeViewHolder onCreateViewHolder(
      final ViewGroup parent,
      final int viewType) {
    return null;
  }

  public class BarrierTypeViewHolder extends RecyclerView.ViewHolder {

    public BarrierTypeViewHolder(final View itemView) {
      super(itemView);
    }
  }
}
