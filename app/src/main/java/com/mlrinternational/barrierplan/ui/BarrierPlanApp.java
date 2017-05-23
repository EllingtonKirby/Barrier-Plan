package com.mlrinternational.barrierplan.ui;

import android.app.Application;
import com.mlrinternational.barrierplan.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

public class BarrierPlanApp extends Application {

  @Override public void onCreate() {
    super.onCreate();
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    LeakCanary.install(this);
    // Normal app init code...
  }
}
