package lj.com.ljstaysafe.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.driving.CheckDrivingStatusService;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent checkDrivingStatusServiceIntent = new Intent(SplashScreenActivity.this, CheckDrivingStatusService.class);
                startService(checkDrivingStatusServiceIntent);
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 500);
    }
}
