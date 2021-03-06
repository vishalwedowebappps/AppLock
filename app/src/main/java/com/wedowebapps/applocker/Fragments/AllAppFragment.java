package com.wedowebapps.applocker.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wedowebapps.applocker.Adapter.ApplicationListAdapter;
import com.wedowebapps.applocker.Adapter.GetListOfAppsAsyncTask;
import com.wedowebapps.applocker.Data.AppInfo;
import com.wedowebapps.applocker.R;

import java.util.ArrayList;
import java.util.List;




public class AllAppFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static String requiredAppsType;

    public static AllAppFragment newInstance(String requiredApps) {
        requiredAppsType = requiredApps;
        AllAppFragment f = new AllAppFragment();
        return (f);
    }


    public AllAppFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ProgressDialog progressDialog;
    List<AppInfo> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_all_apps, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Apps ...");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ApplicationListAdapter(list , getActivity(), requiredAppsType);
        mRecyclerView.setAdapter(mAdapter);

        GetListOfAppsAsyncTask task = new GetListOfAppsAsyncTask(this);
        task.execute(requiredAppsType);

        return v;

    }


    public void showProgressBar() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        progressDialog.show();

    }

    public void hideProgressBar(){
        progressDialog.dismiss();
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void updateData(List<AppInfo> list){
        this.list.clear();
        this.list.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
