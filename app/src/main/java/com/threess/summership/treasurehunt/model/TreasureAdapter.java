package com.threess.summership.treasurehunt.model;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;


public class TreasureAdapter extends RecyclerView.Adapter<TreasureAdapter.RecyclerViewHolder> {


    private Context context;
    private ArrayList<Treasure> treasureList = new ArrayList<>();
    public static String TAG = "adapter_fragment";

    public TreasureAdapter(Context context) {
        this.context = context;
        }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.treasure_list_component, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        try {
            Log.e(TAG, "Adapter " + position);
            holder.treasureText.setText(treasureList.get(position).getDescription());
            holder.treasureImage.setImageBitmap(Util.getDrawableTreasureImage(context));
            holder.treasureScore.setText(treasureList.get(position).getPrize_points()+"");
            holder.treasureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" +
                            treasureList.get(position).getLocation_lat()+"," +
                            treasureList.get(position).getLocation_lon()+"&mode=w");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return treasureList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView treasureText;
        private ImageView treasureImage;
        private ImageView treasureButton;
        private TextView treasureScore;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            treasureText = itemView.findViewById(R.id.treasureName);
            treasureImage = itemView.findViewById(R.id.treasureImage);
            treasureButton = itemView.findViewById(R.id.treasureButton);
            treasureScore = itemView.findViewById(R.id.treasureScore);
        }
    }

    public void addTreasureComponent(Treasure treasure){
        treasureList.add(treasure);
        notifyDataSetChanged();
    }

    public void addTreasure(ArrayList<Treasure> treasures){
        treasureList.clear();
        treasureList.addAll(treasures);
        notifyDataSetChanged();
    }

}