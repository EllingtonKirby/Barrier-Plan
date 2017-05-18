package com.mlrinternational.barrierplan.ui.calculate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierItem;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.data.CustomBarrier;
import com.mlrinternational.barrierplan.ui.base.BarrierPlanFragmentListener;
import java.util.ArrayList;
import java.util.List;

public class MultipleBarrierTypeAdapter
    extends RecyclerView.Adapter<MultipleBarrierTypeAdapter.BarrierTypeViewHolder> {

  private static final String format = "%s %s";
  private final List<BarrierItem> items = new ArrayList<>();
  private final MultipleBarrierCalcListener listener;
  private BarrierPlanFragmentListener fragmentListener;
  private Context context;

  public MultipleBarrierTypeAdapter(
      final Context context,
      final MultipleBarrierCalcListener listener,
      final BarrierPlanFragmentListener fragmentListener) {
    this.context = context;
    this.listener = listener;
    this.fragmentListener = fragmentListener;
  }

  @Override public int getItemCount() {
    return items.size();
  }

  @Override public void onBindViewHolder(
      final BarrierTypeViewHolder holder,
      final int position) {
    final BarrierItem item = items.get(position);

    if (item instanceof BarrierType) {
      holder.textType.setVisibility(View.GONE);
      holder.imgType.setVisibility(View.VISIBLE);
      holder.imgType.setImageDrawable(context
          .getResources()
          .getDrawable(((BarrierType) item).getLogo()));
    } else if (item instanceof CustomBarrier) {
      holder.textType.setText(item.getType());
    }

    RxTextView
        .textChanges(holder.editText)
        .filter(charSequence -> charSequence.length() > 0)
        .subscribe(
            charSequence -> {
              final Double value = Double.valueOf(charSequence.toString());
              final String barrier = value == 1 ? "Barrier" : "Barriers";
              final int numBarriers = fragmentListener.getCalculation(value, item).first;
              final String result = String.format(
                  format,
                  numBarriers,
                  barrier
              );
              holder.barrierTotal.setText(result);
              listener.updateTotals(item, numBarriers);
            }
        );
  }

  @Override public BarrierTypeViewHolder onCreateViewHolder(
      final ViewGroup parent,
      final int viewType) {
    return new BarrierTypeViewHolder(
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_mult_barrier_calculation, parent, false)
    );
  }

  public void addItem(final BarrierItem item) {
    items.add(item);
    notifyDataSetChanged();
  }

  class BarrierTypeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.entry_length) EditText editText;
    @BindView(R.id.img_type) ImageView imgType;
    @BindView(R.id.title) TextView textType;
    @BindView(R.id.barrier_total) TextView barrierTotal;

    public BarrierTypeViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
