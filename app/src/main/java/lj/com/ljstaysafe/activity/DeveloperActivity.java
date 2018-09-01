package lj.com.ljstaysafe.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.view.recyclerview_adapter.RecyclerViewDeveloperLogAdapter;
import lj.com.ljstaysafe.contract.DeveloperLogContract;
import lj.com.ljstaysafe.model.DeveloperLog;
import lj.com.ljstaysafe.model.SensorHelper;
import lj.com.ljstaysafe.presenter.DeveloperLogPresenterImpl;

public class DeveloperActivity extends AppCompatActivity implements Button.OnClickListener, DeveloperLogContract.View {

    private static SimpleDateFormat SIMPLE_DATE_FORMAT;
    private StringBuilder stringBuilder;

    private RecyclerView rvDeveloperLog;
    private RecyclerViewDeveloperLogAdapter rvDeveloperLogAdapter;
    private Button btnTakeNote;
    private DeveloperLogContract.Presenter presenter;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        stringBuilder = new StringBuilder();

        rvDeveloperLog = findViewById(R.id.rvDeveloperLog);
        rvDeveloperLog.setLayoutManager(new LinearLayoutManager(this));

        presenter = new DeveloperLogPresenterImpl(this, this);
        presenter.loadLogs();

        btnTakeNote = findViewById(R.id.btnTakeNote);
        btnTakeNote.setOnClickListener(this);

        showAlertDialog();
    }

    private void showAlertDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(DeveloperActivity.this, R.style.AppTheme_AlertDialog)
                .setCancelable(false)
                .setTitle("JANGAN LUPA!")
                .setMessage("Selalu catat log untuk kondisi berkendara yang baik terlebih dahulu " +
                        "sebelum mencatat log untuk kondisi berkendara yang buruk.\n" +
                        "Ini dikarenakan datanya dibutuhkan sebagai pembanding antara kondisi sensor saat berkendara baik dan buruk itu bagaimana.")
                .setPositiveButton("OKE SIYAP!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTakeNote:
                presenter.saveLog(DeveloperLog.builder()
                        .id(UUID.randomUUID().toString())
                        .date(SIMPLE_DATE_FORMAT.format(new Date()))
                        .value(
                                String.valueOf(stringBuilder
                                        .append("x : ").append(String.valueOf(SensorHelper.ACC_CURRENT_X))
                                        .append("\ny : ").append(String.valueOf(SensorHelper.ACC_CURRENT_Y))
                                        .append("\nz : ").append(String.valueOf(SensorHelper.ACC_CURRENT_Z)))
                        )
                        .build());
                stringBuilder.setLength(0);
                presenter.loadLogs();
                break;
        }
    }

    @Override
    public void loadDeveloperLog(List<DeveloperLog> developerLogList) {
        rvDeveloperLogAdapter = new RecyclerViewDeveloperLogAdapter(developerLogList);
        rvDeveloperLog.setAdapter(rvDeveloperLogAdapter);
        rvDeveloperLogAdapter.notifyDataSetChanged();
    }
}
