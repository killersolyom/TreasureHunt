package com.threess.summership.treasurehunt.model;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.threess.summership.treasurehunt.R;

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
    public void onBindViewHolder( final RecyclerViewHolder holder, int position) {
        try {
            Log.e(TAG, "Adapter " + position);
            holder.treasureText.setText(treasureList.get(position).getDescription());
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

        RecyclerViewHolder(View itemView) {
            super(itemView);
            treasureText = itemView.findViewById(R.id.treasureName);
            treasureImage = itemView.findViewById(R.id.treasureImage);
            treasureButton = itemView.findViewById(R.id.treasureButton);
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