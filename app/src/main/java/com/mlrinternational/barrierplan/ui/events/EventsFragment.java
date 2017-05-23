package com.mlrinternational.barrierplan.ui.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.Event;
import com.mlrinternational.barrierplan.data.EventList;
import com.mlrinternational.barrierplan.ui.base.BaseBarrierPlanFragment;

public class EventsFragment extends BaseBarrierPlanFragment implements ShowEventDetailsListener {

  @BindView(R.id.sort) Spinner sortBy;
  @BindView(R.id.event_list) RecyclerView eventList;
  @BindView(R.id.event_name) TextView eventName;
  @BindView(R.id.event_details) RecyclerView eventDetails;

  private DividerItemDecoration itemDecoration;
  private EventListAdapter eventListAdapter;
  private EventDetailsAdapter eventDetailsAdapter;
  private EventList events;

  public static EventsFragment getInstance() {
    return new EventsFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_events;
  }

  @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    events = listener.getSavedEvents();
    itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
    itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.color.gray));
    eventListAdapter = new EventListAdapter(getActivity(), this);
    eventDetailsAdapter = new EventDetailsAdapter(getActivity(), this);
  }

  @Override public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (events != null) {
      eventListAdapter.setEventList(events.getEvents());
      eventList.setLayoutManager(new LinearLayoutManager(getActivity()));
      eventList.setAdapter(eventListAdapter);
      eventList.addItemDecoration(itemDecoration);
      eventDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
      eventDetails.addItemDecoration(itemDecoration);
      eventDetails.setAdapter(eventDetailsAdapter);
    }
    listener.showContactToolbar();
  }

  @Override public void deleteItemAt(final int position) {
    events.getEvents().remove(position);
    final boolean removed = listener.removeEvent(position);
    if (removed) {
      listener.showSnackBar("Event Successfully Deleted");
    }
    eventListAdapter.setEventList(events.getEvents());
  }

  @Override public void showDetails(final int position, final String title) {
    final Event event = events.getEvents().get(position);
    eventDetailsAdapter.setItems(event.getItemsByAmount());
    eventName.setText(getString(R.string.event_details_format, title));
  }
}
