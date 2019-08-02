package com.threess.summership.treasurehunt.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;


public class TopListRecycleViewAdapter extends RecyclerView.Adapter<TopListRecycleViewAdapter.TopListViewHolder> {
    private static final String TAG = TopListRecycleViewAdapter.class.getSimpleName();
    private Context context;

    public TopListRecycleViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toplist_item_layout, parent, false);
        return new TopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopListViewHolder holder, final int position) {
        holder.nameTextView.setText(Util.userList.get(position).getUsername());
        holder.scoreNumberTextView.setText( String.valueOf(Util.userList.get(position).getScore()));
        Glide.with(context)
                .load(Constant.ApiController.BASE_URL +
                        Util.userList.get(position).getProfilpicture())
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.default_pic)
                .override(250)
                .placeholder(context
                        .getDrawable(R.drawable.default_pic)).into(holder.pictureImageView);

        holder.itemView.setOnClickListener(view -> {
            if (Util.userList.get(position) != null) {
                showUserProfile(Util.userList.get(position),
                        Constant.ApiController.BASE_URL +
                                Util.userList.get(position).getProfilpicture());
            }
        });


    }

    private void showUserProfile(User user, String imageUrl){
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.alert_dialog_user_profile, null);
        ImageView profilePicture = view.findViewById(R.id.dialog_imageview);
        TextView userName = view.findViewById(R.id.dialog_username);
        TextView userScore = view.findViewById(R.id.dialog_userscore);
        userName.setText(context.getString(R.string.username) +": " + user.getUsername());
        userScore.setText(context.getString(R.string.score)+ ": " + user.getScore());
        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.default_pic)
                .into(profilePicture);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setView(view);
        alertDialog.show();
    }

    public void addComponents(ArrayList<User> users){
        Util.userList.clear();
        Util.userList.addAll(users);
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return Util.userList.isEmpty();
    }

    @Override
    public int getItemCount() {
        return Util.userList.size();
    }

    public static class TopListViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView scoreNumberTextView;
        public ImageView pictureImageView;
        public TopListViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            scoreNumberTextView = itemView.findViewById(R.id.scoreNumberTextView);
            pictureImageView = itemView.findViewById(R.id.pictureImageView);

        }
    }
}
