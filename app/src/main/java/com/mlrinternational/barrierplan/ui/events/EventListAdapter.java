package com.mlrinternational.barrierplan.ui.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.view.RxView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.Event;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListViewHolder> {

  private final Context context;
  private final DateFormat dateFormat;
  private final ShowEventDetailsListener listener;
  private List<Event> eventList;

  public EventListAdapter(
      final Context context,
      final ShowEventDetailsListener listener) {
    this.context = context;
    this.listener = listener;
    dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
  }

  @Override public int getItemCount() {
    return eventList.size();
  }

  @Override public void onBindViewHolder(
      final EventListViewHolder holder,
      final int position) {
    final Event currentEvent = eventList.get(position);
    if (currentEvent != null) {
      final String title =
          String.format(
              "%s (%s)",
              dateFormat.format(currentEvent.getDate()),
              currentEvent.getName()
          );
      holder.title.setText(title);
      RxView.clicks(holder.delete)
          .subscribe(
              o -> listener.deleteItemAt(position)
          );
      RxView.clicks(holder.itemView)
          .subscribe(
              o -> listener.showDetails(position, title)
          );
    }
  }

  @Override public EventListViewHolder onCreateViewHolder(
      final ViewGroup parent,
      final int viewType) {
    return new EventListViewHolder(
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_event_list, parent, false)
    );
  }

  public void setEventList(final List<Event> eventList) {
    this.eventList = eventList;
    notifyDataSetChanged();
  }

  public class EventListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.delete) ImageView delete;

    public EventListViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
