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
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.model.User;
import com.threess.summership.treasurehunt.util.Constant;

import java.util.ArrayList;


public class TopListRecycleViewAdapter extends RecyclerView.Adapter<TopListRecycleViewAdapter.TopListViewHolder> {
    private static final String TAG = TopListRecycleViewAdapter.class.getSimpleName();

    private ArrayList<User> list;

    private Context context;

    public TopListRecycleViewAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toplist_item_layout, parent, false);

        return new TopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopListViewHolder holder, final int position) {

        holder.nameTextView.setText(list.get(position).getUsername());
        /*holder.scoreNumberTextView.setText(
                context.getResources().getString(R.string.top_list_score)
                        + list.get(position).getScore());*/
        holder.scoreNumberTextView.setText(String.valueOf( list.get(position).getScore() ));
        Glide.with(context)
                .load(Constant.ApiController.BASE_URL + list.get(position).getProfilpicture())
                .circleCrop()
                .error(R.drawable.default_pic)
                .placeholder(context
                        .getDrawable(R.drawable.default_pic)).into(holder.pictureImageView);

        holder.itemView.setOnClickListener(view -> {
            if (list.get(position) != null) {
                showUserProfile(list.get(position),
                        Constant.ApiController.BASE_URL + list.get(position).getProfilpicture());
            }
        });


    }

    private void showUserProfile(User user, String imageUrl){
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.custom_alert_dialog_view, null);
        ImageView profilePicture = view.findViewById(R.id.dialog_imageview);
        TextView userName = view.findViewById(R.id.dialog_username);
        TextView userScore = view.findViewById(R.id.dialog_userscore);
        userName.setText(context.getString(R.string.Username) +": " + user.getUsername());
        userScore.setText(context.getString(R.string.score)+ ": " + user.getScore());
        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .error(R.drawable.default_pic)
                .into(profilePicture);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
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
