package com.threess.summership.treasurehunt.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Treasure;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Constant;

import java.util.ArrayList;


public class TreasureAdapter extends RecyclerView.Adapter<TreasureAdapter.RecyclerViewHolder> {


    private Context context;
    private ArrayList<Treasure> treasureList = new ArrayList<>();
    private Treasure selectedTreasure = null;
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
            final Treasure treasure = treasureList.get(position);
            Glide.with(context).load(treasure.getPhoto_clue()).error(R.drawable.app_icon).circleCrop().into(holder.treasureImage);
            holder.treasureText.setText(treasure.getDescription());
            holder.treasureScore.setText(String.valueOf(treasure.getPrize_points()));

            if(treasure.isClaimed()){
                holder.treasureButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check_circle_black_24dp));
            }

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedTreasure = treasure;
                    FragmentNavigation.getInstance(context).
                             startNavigationToDestination(treasure,context);

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
        private ConstraintLayout layout;
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
            layout = itemView.findViewById(R.id.treasureListComponentLayout);
        }
    }

    public void addTreasureComponent(Treasure treasure){
        treasureList.add(treasure);
        notifyDataSetChanged();
    }

    public void refreshTreasure(ArrayList<Treasure> treasures){
        if(treasures.size()!=0) {
            treasureList.clear();
        }

        //treasureList.addAll(treasures);

        for(Treasure t : treasures){
            if (t != null) {
                if (t.getClaimed_by().equals(new SavedData(context).readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY)) || t.isClaimed() == false) {
                    treasureList.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

    public Treasure getSelectedTreasure() {
        return selectedTreasure;
    }
}