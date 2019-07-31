package com.threess.summership.treasurehunt.fragment.home_menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopListFragment extends Fragment {
    private static final String TAG = TopListFragment.class.getSimpleName();

    private RecyclerView recyclerview;
    private TopListRecycleViewAdapter adapter;
    private RecyclerView.LayoutManager layoutmanager;
    private ArrayList<User> list = new ArrayList<>();

    public TopListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toplist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview  = view.findViewById(R.id.contactList);
        recyclerview.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(view.getContext());
        recyclerview.setLayoutManager(layoutmanager);

        ApiController.getInstance().getAllUsers(
                new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        list = response.body();

                        Collections.sort(list, (user, user2) -> Integer.compare(user2.getScore(), user.getScore()));
                        adapter = new TopListRecycleViewAdapter(list,getContext());
                        recyclerview.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.e (TAG , "failure: ",t);
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}
