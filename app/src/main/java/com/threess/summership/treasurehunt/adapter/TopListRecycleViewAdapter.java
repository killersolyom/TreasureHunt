package com.threess.summership.treasurehunt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.model.User;

import java.util.ArrayList;

import static com.threess.summership.treasurehunt.service.UserRetrofitService.BASE_URL;

public class TopListRecycleViewAdapter extends RecyclerView.Adapter<TopListRecycleViewAdapter.TopListViewHolder> {

    private ArrayList<User> list;

    private Context ctx;
    public TopListRecycleViewAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        ctx=context;
    }


    @Override
    public TopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toplist_item_layout,parent , false);

        return new TopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopListViewHolder holder, int position) {

        holder.text1.setText(list.get(position).getUsername());
        holder.text2.setText(ctx.getResources().getString(R.string.toplist_score) + list.get(position).getScore());
        Glide.with(ctx).load( BASE_URL + list.get(position).getProfilpicture()).placeholder(ctx.getDrawable(R.drawable.default_pic)).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TopListViewHolder extends RecyclerView.ViewHolder{
        public TextView text1;
        public TextView text2;
        public ImageView image;

        public TopListViewHolder(View itemView) {

            super(itemView);
            text1 = itemView.findViewById(R.id.nameTextView);
            text2 = itemView.findViewById(R.id.telNumberTextView);
            image = itemView.findViewById(R.id.icon);

        }
    }
}
