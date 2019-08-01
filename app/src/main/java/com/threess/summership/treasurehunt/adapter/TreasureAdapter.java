package com.threess.summership.treasurehunt.adapter;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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

    private static final String TAG = TreasureAdapter.class.getSimpleName();
    private Context mContext;
    private static ArrayList<Treasure> mTreasureList = new ArrayList<>();
    private Treasure mSelectedTreasure = null;
    private static boolean passcodeCheck;


    public TreasureAdapter(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.treasure_list_component, parent, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        try {
            final Treasure treasure = mTreasureList.get(position);
            Glide.with(mContext).load(treasure.getPhotoClue()).error(R.drawable.app_icon).circleCrop().into(holder.mTreasureImage);
            holder.mTreasureText.setText(treasure.getDescription());

            double score = treasure.getPrizePoints();
            int roundedScore = (int) Math.round(score);

            if( score == roundedScore){
                holder.mTreasureScore.setText("+" + roundedScore);
            }else{
                holder.mTreasureScore.setText("+"+ score);
            }

            if(treasure.isClaimed()){
                holder.mTreasureButton.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_check_circle_black_24dp));
            }

            holder.mLayout.setOnClickListener(v -> {
                mSelectedTreasure = treasure;
                FragmentNavigation.getInstance(mContext).
                        startNavigationToDestination(treasure, mContext);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean returnPasscodeCheck(String passcode){
        return  passcodeCheck;

    }

    public static boolean checkThisPasscodeIsAvailable(String passcode){
        for (Treasure treasure: mTreasureList)
        {
            if(treasure.getPasscode().equals(passcode))return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return mTreasureList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout mLayout;
        private TextView mTreasureText;
        private ImageView mTreasureImage;
        private ImageView mTreasureButton;
        private TextView mTreasureScore;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.treasureListComponentLayout);
            mTreasureText = itemView.findViewById(R.id.treasureName);
            mTreasureImage = itemView.findViewById(R.id.treasureImage);
            mTreasureButton = itemView.findViewById(R.id.treasureButton);
            mTreasureScore = itemView.findViewById(R.id.treasureScore);
        }
    }

    public void setTreasureList(ArrayList<Treasure> treasures){
        if(treasures.size()!=0) {
            mTreasureList.clear();
        }

        for(Treasure t : treasures){
            if (t != null) {
                if (t.getClaimedBy().equals(new SavedData(mContext).readStringData(Constant.SavedData.USER_PROFILE_NAME_KEY)) || !t.isClaimed()) {
                    mTreasureList.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

    public Treasure getSelectedTreasure() {
        return mSelectedTreasure;
    }

    public void clearSelectedTreasure(){
        mSelectedTreasure = null;
    }


}