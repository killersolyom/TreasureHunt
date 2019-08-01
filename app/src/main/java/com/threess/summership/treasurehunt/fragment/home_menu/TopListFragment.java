package com.threess.summership.treasurehunt.fragment.home_menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.adapter.TopListRecycleViewAdapter;
import com.threess.summership.treasurehunt.logic.ApiController;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.util.Constant;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopListFragment extends Fragment {
    private static final String TAG = TopListFragment.class.getSimpleName();

    private RecyclerView recyclerview;
    private TopListRecycleViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private Runnable runnable;

    public TopListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toplist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        getAllUser();
    }

    private void initComponents(View view){
        adapter = new TopListRecycleViewAdapter(getContext());
        recyclerview  = view.findViewById(R.id.contactList);
        recyclerview.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerview.setLayoutManager(layoutManager);
        initRunnable();
        initSwipeRefreshLayout(view);
    }

    private void initRunnable(){
        runnable = () -> {
            if(getContext()!=null){
                swipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    private void initSwipeRefreshLayout(View view){
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_top_list);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getAllUser();
            swipeRefreshLayout.postDelayed(runnable,Constant.FavoriteTreasure.STOP_SWIPE_REFRESHING_TIME);
        });
    }

    private void getAllUser(){
        ApiController.getInstance().getAllUsers(
                new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        list = response.body();
                        Collections.sort(list, (user, user2) -> Integer.compare(user2.getScore(), user.getScore()));
                        adapter.addComponents(list);
                    }
                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.e (TAG , "failure: ",t);
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}
