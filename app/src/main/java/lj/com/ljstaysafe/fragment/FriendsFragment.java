package lj.com.ljstaysafe.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.view.recyclerview_adapter.RecyclerViewFriendsAdapter;
import lj.com.ljstaysafe.contract.FriendsContract;
import lj.com.ljstaysafe.dialog.AddFriendDialog;
import lj.com.ljstaysafe.model.Friend;
import lj.com.ljstaysafe.presenter.FriendsPresenterImpl;

public class FriendsFragment extends Fragment implements FriendsContract.View, View.OnClickListener {

    private static final String ADD_FRIEND_DIALOG_TAG = "add_friend_dialog";
    private List<Friend> friends = new ArrayList<>();

    private RecyclerView rvFriends;
    private RecyclerViewFriendsAdapter rvFriendsAdapter;
    private FloatingActionButton fabAddFriend;

    private ProgressDialog progressDialog;

    private FriendsContract.Presenter presenter;

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
        rvFriendsAdapter = new RecyclerViewFriendsAdapter(friends);
        rvFriends.setAdapter(rvFriendsAdapter);
        fabAddFriend = view.findViewById(R.id.fabAddFriend);
        fabAddFriend.setOnClickListener(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);

        presenter = new FriendsPresenterImpl(getActivity(), this);
        presenter.loadFriends();

        return view;
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
    public void showFriends(List<Friend> friends) {
        this.friends.clear();
        this.friends.addAll(friends);
        rvFriendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        switch (v.getId()){
            case R.id.fabAddFriend:
                DialogFragment addFriendDialog = AddFriendDialog.newInstance(presenter);
                addFriendDialog.setCancelable(true);
                addFriendDialog.show(fragmentManager, ADD_FRIEND_DIALOG_TAG);
                break;
        }
    }
}
