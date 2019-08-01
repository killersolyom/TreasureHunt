package com.threess.summership.treasurehunt.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.threess.summership.treasurehunt.MainActivity;
import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.model.Language;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageRecyclerViewAdapter extends RecyclerView.Adapter<LanguageRecyclerViewAdapter.RecyclerViewHolder> {

    private static final String TAG = LanguageRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Language> mLanguageList = new ArrayList<>();
    private Language mSelectedLanguage = null;


    public LanguageRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.language_select_item, parent, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        try {
            final Language language = mLanguageList.get(position);

            // Set flag image:
            Glide.with(mContext)
                    .load(language.getDrawableId())
                    .centerCrop()
                    .error(R.drawable.circle_white)
                    .circleCrop()
                    .into(holder.mFlagImage);

            // Set language name:
            holder.mLanguageName.setText(language.getName());

            if(new SavedData(mContext).getLanguage().getKey().equals(language.getKey())) {
                holder.mLanguageName.setTextColor(mContext.getResources().getColor( R.color.blue300) );
            }

            holder.mLayout.setOnClickListener(v -> {
                mSelectedLanguage = language;

                // Save language: (Restart needed)
                new SavedData(mContext).setLanguage( language );

                new Handler().postDelayed(Util::restartApp,250); // Call restartApp() after 250 ms.

                // Load language: (Layout refresh needed)
                //Util.changeApplicationLanguage(mContext, language);
                //((MainActivity)mContext).recreate(); // Refresh layout
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mLanguageList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLayout;
        private ImageView mFlagImage;
        private TextView mLanguageName;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.language_item_linear_layout);
            mFlagImage = itemView.findViewById(R.id.flag_image_view);
            mLanguageName = itemView.findViewById(R.id.language_name_text_view);
        }
    }

    public void setDataSet(ArrayList<Language> languages){
        if( ! languages.isEmpty() ) {
            mLanguageList.clear();
        }else{
            return;
        }

        for(Language l : languages){
            mLanguageList.add(l);
        }

        notifyDataSetChanged();
    }

    public Language getSelectedLanguage() {
        return mSelectedLanguage;
    }

    public void clearSelectedLanguage(){
        mSelectedLanguage = null;
    }


}