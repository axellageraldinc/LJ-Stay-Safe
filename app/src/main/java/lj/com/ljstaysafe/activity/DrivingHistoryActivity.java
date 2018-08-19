package lj.com.ljstaysafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.RecyclerViewDrivingHistoryAdapter;
import lj.com.ljstaysafe.model.DrivingHistory;

public class DrivingHistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvDrivingHistory;
    private RecyclerViewDrivingHistoryAdapter rvDrivingHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_history);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvDrivingHistory = findViewById(R.id.rvDrivingHistory);
        rvDrivingHistory.setLayoutManager(new LinearLayoutManager(DrivingHistoryActivity.this));
        rvDrivingHistoryAdapter = new RecyclerViewDrivingHistoryAdapter(new ArrayList<DrivingHistory>());
        rvDrivingHistory.setAdapter(rvDrivingHistoryAdapter);
        rvDrivingHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
