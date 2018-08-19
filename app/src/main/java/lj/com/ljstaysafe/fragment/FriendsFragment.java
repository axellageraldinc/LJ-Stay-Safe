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
import lj.com.ljstaysafe.RecyclerViewFriendsAdapter;
import lj.com.ljstaysafe.model.Friend;

public class FriendsFragment extends Fragment {

    private RecyclerView rvFriends;
    private RecyclerViewFriendsAdapter rvFriendsAdapter;


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
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        rvFriends = view.findViewById(R.id.rvFriends);
        rvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFriendsAdapter = new RecyclerViewFriendsAdapter(new ArrayList<Friend>());
        rvFriends.setAdapter(rvFriendsAdapter);
        rvFriendsAdapter.notifyDataSetChanged();
        return view;
    }

}
