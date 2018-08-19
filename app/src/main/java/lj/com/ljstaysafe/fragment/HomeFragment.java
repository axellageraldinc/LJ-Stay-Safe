package lj.com.ljstaysafe.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.RecyclerViewHomeAdapter;
import lj.com.ljstaysafe.model.Feed;

public class HomeFragment extends Fragment {

    private RecyclerView rvHome;
    private RecyclerViewHomeAdapter rvHomeAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvHome = view.findViewById(R.id.rvHome);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHomeAdapter = new RecyclerViewHomeAdapter(new ArrayList<Feed>());
        rvHome.setAdapter(rvHomeAdapter);
        rvHomeAdapter.notifyDataSetChanged();
        return  view;
    }

}
