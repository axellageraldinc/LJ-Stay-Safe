package lj.com.ljstaysafe.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.view.recyclerview_adapter.RecyclerViewHomeAdapter;
import lj.com.ljstaysafe.contract.HomeContract;
import lj.com.ljstaysafe.model.Feed;
import lj.com.ljstaysafe.presenter.HomePresenterImpl;

public class HomeFragment extends Fragment implements HomeContract.View {

    private List<Feed> feeds = new ArrayList<>();

    private RecyclerView rvHome;
    private RecyclerViewHomeAdapter rvHomeAdapter;

    private ProgressDialog progressDialog;

    private HomeContract.Presenter presenter;

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
        rvHomeAdapter = new RecyclerViewHomeAdapter(feeds);
        rvHome.setAdapter(rvHomeAdapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);

        presenter = new HomePresenterImpl(getActivity(), this);
        presenter.loadFeeds();

        return  view;
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
    public void showFeeds(List<Feed> feeds) {
        this.feeds.clear();
        this.feeds.addAll(feeds);
        rvHomeAdapter.notifyDataSetChanged();
    }
}
