package com.mlrinternational.barrierplan.ui.events;

interface ShowEventDetailsListener {
  void deleteItemAt(int position);

  void showDetails(int position, final String title);
}
