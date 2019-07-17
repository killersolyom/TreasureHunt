package com.threess.summership.treasurehunt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TopListRecycleViewAdapter extends RecyclerView.Adapter<TopListRecycleViewAdapter.TopListViewHolder> {


    @Override
    public TopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TopListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TopListViewHolder extends RecyclerView.ViewHolder{

        public TopListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
