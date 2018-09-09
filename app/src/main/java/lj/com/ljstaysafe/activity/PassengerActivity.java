package lj.com.ljstaysafe.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.driving.NotificationHandler;
import lj.com.ljstaysafe.repository.audio.AudioRepository;
import lj.com.ljstaysafe.repository.audio.AudioRepositoryImpl;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;

public class PassengerActivity extends AppCompatActivity implements ImageView.OnClickListener {

    private ImageView ivPassenger;
    private TextView tvClickIcon;
    private DrivingStatusContract.Repository drivingStatusRepository;
    private NotificationHandler notificationHandler;

    private AudioManager audioManager;
    private AudioRepository audioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        drivingStatusRepository = new DrivingStatusRepositoryImpl(this);
        notificationHandler = new NotificationHandler(this);

        ivPassenger = findViewById(R.id.ivPassenger);
        tvClickIcon = findViewById(R.id.tvClickIcon);
        ivPassenger.setOnClickListener(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioRepository = new AudioRepositoryImpl(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivPassenger:
                drivingStatusRepository.savePassengerStatus(true);
                drivingStatusRepository.saveDrivingStatus(false);
                tvClickIcon.setText(R.string.enjoy_your_ride);
                ivPassenger.setImageDrawable(getDrawable(R.drawable.ic_happy));
                notificationHandler.createNotification(
                        false,
                        "Enjoy your ride!",
                        "",
                        false);
                audioManager.setRingerMode(audioRepository.getSavedRingerMode());
                break;
        }
    }
}
