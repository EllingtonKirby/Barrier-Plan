package com.mlrinternational.barrierplan.ui.events;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierItem;
import com.mlrinternational.barrierplan.data.BarrierType;
import java.util.ArrayList;
import java.util.List;

public class EventDetailsAdapter
    extends RecyclerView.Adapter<EventDetailsAdapter.EventDetailsViewHolder> {

  private List<Pair<BarrierItem, Integer>> items = new ArrayList<>();
  private Context context;
  private ShowEventDetailsListener listener;

  public EventDetailsAdapter(final Context context, final ShowEventDetailsListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override public int getItemCount() {
    return items.size();
  }

  @Override public void onBindViewHolder(
      final EventDetailsViewHolder holder,
      final int position) {
    final Pair<BarrierItem, Integer> item = items.get(position);
    if (item != null) {
      if (item.first instanceof BarrierType) {
        holder.image.setImageDrawable(ContextCompat.getDrawable(
            context,
            ((BarrierType) (item.first)).getLogo()
        ));
      } else {
        holder.image.setImageDrawable(ContextCompat.getDrawable(
            context,
            R.drawable.ic_indeterminate_check_box_black_24dp
        ));
      }
      final double totalLength = Math.round((item.first.getLengthImperial() * item.second)/12);
      final String content = context.getString(
          R.string.details_content,
          item.first.getType(),
          String.valueOf(item.second),
          String.valueOf(totalLength)
      );
      holder.content.setText(content);
    }
  }

  @Override public EventDetailsViewHolder onCreateViewHolder(
      final ViewGroup parent,
      final int viewType) {
    return new EventDetailsViewHolder(
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_event_detail, parent, false)
    );
  }

  public void setItems(final List<Pair<BarrierItem, Integer>> items) {
    this.items.clear();
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  public class EventDetailsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image) ImageView image;
    @BindView(R.id.content) TextView content;

    public EventDetailsViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
