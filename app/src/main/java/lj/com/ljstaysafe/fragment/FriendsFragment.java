package lj.com.ljstaysafe.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lj.com.ljstaysafe.R;

public class FriendsFragment extends Fragment {


    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance(){
        return new FriendsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

}
