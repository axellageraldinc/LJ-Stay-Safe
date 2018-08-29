package lj.com.ljstaysafe.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.activity.DrivingHistoryActivity;
import lj.com.ljstaysafe.activity.SettingsActivity;
import lj.com.ljstaysafe.contract.MeContract;
import lj.com.ljstaysafe.presenter.MePresenterImpl;

public class MeFragment extends Fragment implements ConstraintLayout.OnClickListener, MeContract.View {

    private ConstraintLayout layoutSettings, layoutDrivingHistory;
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
}
