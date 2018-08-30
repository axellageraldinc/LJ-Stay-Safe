package lj.com.ljstaysafe.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.activity.DrivingHistoryActivity;
import lj.com.ljstaysafe.activity.LoginActivity;
import lj.com.ljstaysafe.activity.SettingsActivity;
import lj.com.ljstaysafe.contract.MeContract;
import lj.com.ljstaysafe.presenter.MePresenterImpl;

public class MeFragment extends Fragment implements ConstraintLayout.OnClickListener, MeContract.View {

    private ConstraintLayout layoutSettings, layoutDrivingHistory, layoutLogout;
    private TextView tvUserFullname, tvLjPoints;

    private ProgressDialog progressDialog;

    private MeContract.Presenter presenter;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(){
        return new MeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_me, container, false);
        layoutSettings = view.findViewById(R.id.layoutSettings);
        layoutSettings.setOnClickListener(this);
        layoutDrivingHistory = view.findViewById(R.id.layoutDrivingHistory);
        layoutDrivingHistory.setOnClickListener(this);
        layoutLogout = view.findViewById(R.id.layoutLogout);
        layoutLogout.setOnClickListener(this);
        tvUserFullname = view.findViewById(R.id.tvUserFullname);
        tvLjPoints = view.findViewById(R.id.tvLjPoints);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data...");
        progressDialog.setIndeterminate(true);

        presenter = new MePresenterImpl(this, getActivity());
        presenter.loadUserProfile();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutSettings:
                Intent settingsActivity = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            case R.id.layoutDrivingHistory:
                Intent drivingHistoryActivity = new Intent(getActivity(), DrivingHistoryActivity.class);
                startActivity(drivingHistoryActivity);
                break;
            case R.id.layoutLogout:
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setCancelable(true)
                        .setTitle("Logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.logout();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alertDialog.show();
                break;
        }
    }

    @Override
    public void setUserFullname(String fullname) {
        tvUserFullname.setText(fullname);
    }

    @Override
    public void setLjPoints(String ljPoints) {
        tvLjPoints.setText(ljPoints);
    }

    @Override
    public void showLoadingView() {
        progressDialog.show();
    }

    @Override
    public void dismissLoadingView() {
        progressDialog.dismiss();
    }

    @Override
    public void moveToLoginPage() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }
}
