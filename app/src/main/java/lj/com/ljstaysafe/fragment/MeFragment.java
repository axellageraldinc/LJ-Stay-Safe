package lj.com.ljstaysafe.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.activity.SettingsActivity;

public class MeFragment extends Fragment implements ConstraintLayout.OnClickListener {

    private ConstraintLayout layoutSettings;


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
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutSettings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
