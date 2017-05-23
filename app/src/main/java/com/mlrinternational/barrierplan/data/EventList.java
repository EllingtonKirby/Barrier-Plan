package com.mlrinternational.barrierplan.data;

import android.util.Pair;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventList {

  public static final String NAME = "name";
  public static final String DATE = "date";
  public static final String BARRIER = "barrier";
  public static final String BARRIER_LIST = "barrierList";
  public static final String NUMBER = "number";
  public static final String DD_MM_YYYY = "dd-MM-yyyy";
  private List<Event> events;

  public EventList(final List<Event> events) {
    this.events = events;
  }

  public EventList() {
    events = new ArrayList<>();
  }

  public static EventList deserialize(final String input) throws JSONException, ParseException {
    if (input == null || input.length() <= 2) {
      return null;
    }
    final DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY, Locale.US);
    final EventList eventList = new EventList();
    final JSONArray jsonObject = new JSONArray(input);
    for (int i = 0; i < jsonObject.length(); i++) {
      final JSONObject current = jsonObject.getJSONObject(i);
      final String name = current.getString(NAME);
      final Date date = dateFormat.parse(current.getString(DATE));
      final List<Pair<BarrierItem, Integer>> pairs = new ArrayList<>();
      final JSONArray jsonArray = current.getJSONArray(BARRIER_LIST);
      for (int j = 0; j < jsonArray.length(); j++) {
        final JSONObject barrierJson = jsonArray.getJSONObject(j);
        final BarrierItem barrier =
            BarrierItemDeserializer.getFromJson(barrierJson.getString(BARRIER));
        final Integer number = barrierJson.getInt(NUMBER);
        pairs.add(Pair.create(barrier, number));
      }
      eventList.addEvent(new Event(pairs, name, date));
    }
    return eventList;
  }

  public void addEvent(final Event event) {
    this.events.add(event);
  }

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(final List<Event> events) {
    this.events.addAll(events);
  }

  public String serialize() throws JSONException {
    final JSONArray jsonObject = new JSONArray();
    final DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY, Locale.US);
    for (final Event event : events) {
      final JSONObject eventObject = new JSONObject();
      eventObject.put(NAME, event.getName());
      eventObject.put(DATE, dateFormat.format(event.getDate()));
      final JSONArray jsonArray = new JSONArray();
      for (final Pair<BarrierItem, Integer> pairs : event.getItemsByAmount()) {
        final JSONObject pairObject = new JSONObject();
        pairObject.put(BARRIER, pairs.first.getJsonString());
        pairObject.put(NUMBER, pairs.second);
        jsonArray.put(pairObject);
      }
      eventObject.put(BARRIER_LIST, jsonArray);
      jsonObject.put(eventObject);
    }
    return jsonObject.toString();
  }
}

