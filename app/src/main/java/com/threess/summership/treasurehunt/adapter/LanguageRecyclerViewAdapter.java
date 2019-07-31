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
import com.threess.summership.treasurehunt.model.Language;
import com.threess.summership.treasurehunt.util.Util;

import java.util.ArrayList;

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
                    .error(R.drawable.circle_white)
                    .circleCrop()
                    .into(holder.mFlagImage);

            // Set language name:
            holder.mLanguageName.setText(language.getName());

            holder.mLayout.setOnClickListener(v -> {
                mSelectedLanguage = language;
                Util.ChangeLanguage(language, mContext);
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
        private ConstraintLayout mLayout;
        private ImageView mFlagImage;
        private TextView mLanguageName;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.language_selector_recycler_view);
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