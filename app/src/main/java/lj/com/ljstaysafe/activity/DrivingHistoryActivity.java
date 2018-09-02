package lj.com.ljstaysafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.DrivingHistoryContract;
import lj.com.ljstaysafe.presenter.DrivingHistoryPresenterImpl;
import lj.com.ljstaysafe.view.recyclerview_adapter.RecyclerViewDrivingHistoryAdapter;
import lj.com.ljstaysafe.model.DrivingHistory;

public class DrivingHistoryActivity extends AppCompatActivity implements DrivingHistoryContract.View {

    private List<DrivingHistory> drivingHistories = new ArrayList<>();

    private Toolbar toolbar;
    private RecyclerView rvDrivingHistory;
    private RecyclerViewDrivingHistoryAdapter rvDrivingHistoryAdapter;
    private DrivingHistoryContract.Presenter presenter;

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
        rvDrivingHistoryAdapter = new RecyclerViewDrivingHistoryAdapter(drivingHistories);
        rvDrivingHistory.setAdapter(rvDrivingHistoryAdapter);

        presenter = new DrivingHistoryPresenterImpl(DrivingHistoryActivity.this, this);
        presenter.loadDrivingHistories();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void loadDrivingHistories(List<DrivingHistory> drivingHistoryList) {
        drivingHistories.clear();
        drivingHistories.addAll(drivingHistoryList);
        rvDrivingHistoryAdapter.notifyDataSetChanged();
    }
}
