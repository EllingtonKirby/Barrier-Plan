package com.mlrinternational.barrierplan.ui;

import android.app.Application;
import com.mlrinternational.barrierplan.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

public class BarrierPlanApp extends Application {

  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      if (LeakCanary.isInAnalyzerProcess(this)) {
        return;
      }
      LeakCanary.install(this);
    }
  }
}
