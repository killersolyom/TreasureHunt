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
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopListFragment extends Fragment {

    public static String TAG = TopListFragment.class.getSimpleName();
    private RecyclerView recyclerview;
    private TopListRecycleViewAdapter adapter;
    private RecyclerView.LayoutManager layoutmanager;
    private ArrayList<User> list = new ArrayList<>();
    public TopListFragment() {
        // constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toplist, container, false);
        // Do not modify!
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO

        recyclerview   = view.findViewById(R.id.contactList);
        recyclerview.setHasFixedSize(true);

        layoutmanager = new LinearLayoutManager(view.getContext());
        recyclerview.setLayoutManager(layoutmanager);

        ApiController.getInstance().getAllUsers(

                new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        list = response.body();


                        Collections.sort(list, new Comparator<User>() {
                            @Override
                            public int compare(User user, User user2) {
                                return Integer.compare(user2.getScore(), user.getScore());

                            }
                        });
                        adapter = new TopListRecycleViewAdapter(list,getContext());
                        recyclerview.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.e (TAG , "failure: ",t);
                    }
                });

    }
}
