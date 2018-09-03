package lj.com.ljstaysafe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.SeeDrivingScoreContract;
import lj.com.ljstaysafe.model.DrivingHistory;
import lj.com.ljstaysafe.model.ShareDrivingScoreDestination;
import lj.com.ljstaysafe.presenter.SeeDrivingScorePresenterImpl;

public class SeeDrivingScoreActivity extends AppCompatActivity implements SeeDrivingScoreContract.View, View.OnClickListener {

    private static final String TAG = SeeDrivingScoreActivity.class.getName();

    private DrivingHistory drivingHistory;

    private ProgressDialog progressDialog;

    private TextView tvSuddenBreakScore, tvTurnScore, tvHonkingScore, tvHighSpeedScore, tvDistractedScore, tvOverallScore;
    private Button btnShareOnLjStaySafe;

    private SeeDrivingScoreContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_driving_score);

        progressDialog = new ProgressDialog(SeeDrivingScoreActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);

        tvSuddenBreakScore = findViewById(R.id.tvSuddenBrakeScore);
        tvTurnScore = findViewById(R.id.tvTurnScore);
        tvHonkingScore = findViewById(R.id.tvHonkingScore);
        tvHighSpeedScore = findViewById(R.id.tvHighSpeedScore);
        tvDistractedScore = findViewById(R.id.tvDistractedScore);
        tvOverallScore = findViewById(R.id.tvOverallScore);

        btnShareOnLjStaySafe = findViewById(R.id.btnShareOnLjStaySafe);
        btnShareOnLjStaySafe.setOnClickListener(this);

        presenter = new SeeDrivingScorePresenterImpl(this, SeeDrivingScoreActivity.this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("drivingHistory");
        DrivingHistory drivingHistory = bundle.getParcelable("drivingHistory");
        showDrivingScore(Objects.requireNonNull(drivingHistory));
    }

    @Override
    public void showLoadingView() {
        progressDialog.show();
    }

    @Override
    public void dismissLoadingView() {
        progressDialog.dismiss();
    }

    private void showDrivingScore(DrivingHistory drivingHistory) {
        this.drivingHistory = drivingHistory;
        tvSuddenBreakScore.setText(drivingHistory.getSuddenBrakeScore());
        tvTurnScore.setText(drivingHistory.getTurnScore());
        tvHonkingScore.setText(drivingHistory.getHonkingScore());
        tvHighSpeedScore.setText(drivingHistory.getHighSpeedScore());
        tvDistractedScore.setText(drivingHistory.getDistractedScore());
        tvOverallScore.setText(drivingHistory.getOverallScore());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnShareOnLjStaySafe:
                presenter.shareDrivingScore(drivingHistory, ShareDrivingScoreDestination.STAY_SAFE);
                break;
        }
    }
}
