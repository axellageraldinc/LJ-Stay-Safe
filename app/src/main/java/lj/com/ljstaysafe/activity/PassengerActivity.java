package lj.com.ljstaysafe.activity;

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
import lj.com.ljstaysafe.repository.driving.DrivingStatusInteractorImpl;

public class PassengerActivity extends AppCompatActivity implements ImageView.OnClickListener {

    private ImageView ivPassenger;
    private TextView tvClickIcon;
    private DrivingStatusContract.Interactor drivingStatusInteractor;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        drivingStatusInteractor = new DrivingStatusInteractorImpl(this);
        notificationHandler = new NotificationHandler(this);

        ivPassenger = findViewById(R.id.ivPassenger);
        tvClickIcon = findViewById(R.id.tvClickIcon);
        ivPassenger.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivPassenger:
                drivingStatusInteractor.savePassengerStatus(true);
                drivingStatusInteractor.saveDrivingStatus(false);
                tvClickIcon.setText(R.string.enjoy_your_ride);
                ivPassenger.setImageDrawable(getDrawable(R.drawable.ic_happy));
                notificationHandler.createNotification(
                        false,
                        "Driving is over",
                        "Click here to see your driving score",
                        false);
                break;
        }
    }
}
