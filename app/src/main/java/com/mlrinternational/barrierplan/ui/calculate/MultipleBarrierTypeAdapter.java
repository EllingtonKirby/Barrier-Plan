package com.mlrinternational.barrierplan.ui.calculate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mlrinternational.barrierplan.R;
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
    return new BarrierTypeViewHolder(LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.item_mult_barrier_calculation, parent, false));
  }

  public class BarrierTypeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.entry_length) EditText entryLength;
    @BindView(R.id.img_type) ImageView imgType;
    @BindView(R.id.title) TextView textType;
    @BindView(R.id.barrier_total) TextView barrierTotal;

    public BarrierTypeViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
