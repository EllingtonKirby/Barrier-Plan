package com.mlrinternational.barrierplan.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.ui.landing.LandingActivity;

public class SplashScreenActivity extends AppCompatActivity {

  public static final int DELAY_MILLIS = 2000;

  @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    new Handler().postDelayed(
        () -> {
          LandingActivity.start(this);
          finishAffinity();
        },
        DELAY_MILLIS
    );
  }
}
