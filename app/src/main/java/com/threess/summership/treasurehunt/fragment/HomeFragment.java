package com.threess.summership.treasurehunt.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.threess.summership.treasurehunt.R;
import com.threess.summership.treasurehunt.adapter.LanguageRecyclerViewAdapter;
import com.threess.summership.treasurehunt.adapter.ScreenSlidePagerAdapter;
import com.threess.summership.treasurehunt.logic.SavedData;
import com.threess.summership.treasurehunt.navigation.FragmentNavigation;
import com.threess.summership.treasurehunt.util.Constant;
import com.threess.summership.treasurehunt.util.Util;


public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    public static ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private Button toolbarLanguageButton;
    private Button toolbarMapButton;
    private Button toolbarButton;
    // Language selector:
    private LanguageRecyclerViewAdapter mLanguageAdapter;
    private RecyclerView mLanguageRecyclerView;
    private static AlertDialog.Builder mLanguageAlertDialog;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        setupViewPager();
        setupBottomNavigation();
        bindClickListeners();
    }

    private void bindClickListeners(){
        toolbarButton.setOnClickListener(v -> showLanguageSelector() );
        toolbarLanguageButton.setOnClickListener(v -> {
        });
        toolbarMapButton.setOnClickListener(v -> {
            FragmentNavigation.getInstance(getContext()).showMapViewFragmentInHomeFragment();
        });
    }

    private void showLanguageSelector(){

        LayoutInflater factory = LayoutInflater.from(getContext());
        View view = factory.inflate(R.layout.alert_dialog_select_language, null);

        mLanguageAdapter = new LanguageRecyclerViewAdapter(getContext());
        mLanguageAdapter.setDataSet( Util.getLanguages(getContext()) );

        mLanguageRecyclerView = view.findViewById(R.id.language_selector_recycler_view);
        mLanguageRecyclerView.setAdapter( mLanguageAdapter );
        mLanguageRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mLanguageAlertDialog = new AlertDialog.Builder(getContext());
        mLanguageAlertDialog.setView(view);
        //mLanguageAlertDialog.show();
        mLanguageAlertDialog.show();
        view.setOnClickListener( v -> mLanguageAlertDialog.create().dismiss());
    }

    private void bindViews(View view) {
        viewPager = view.findViewById(R.id.home_viewpager);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        toolbar = view.findViewById(R.id.home_toolbar);
        toolbarLanguageButton = toolbar.findViewById(R.id.toolbar_settings);
        toolbarMapButton = toolbar.findViewById(R.id.toolbar_map_view);
    }

    @SuppressWarnings("deprecation")
    private void setupViewPager() {
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new PageChange());
        viewPager.setCurrentItem(1);
    }

    public static void showPage(int idx){
        if( idx > 0 && idx <= 4 ) {
            viewPager.setCurrentItem(idx);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_profile: {
                            viewPager.setCurrentItem(Constant.HomeViewPager.PROFILE_IDX);
                            return true;
                        }
                        case R.id.action_recent: {
                            viewPager.setCurrentItem(Constant.HomeViewPager.TREASURE_IDX);
                            return true;
                        }
                        case R.id.action_favorites: {
                            viewPager.setCurrentItem(Constant.HomeViewPager.TOPLIST_IDX);
                            return true;
                        }
                        case R.id.action_hide_treasure: {
                            viewPager.setCurrentItem(3);
                            viewPager.setCurrentItem(Constant.HomeViewPager.HIDE_TREASURE_IDX);
                            return true;
                        }
                    }
                    return false;
                });
    }

    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case Constant.HomeViewPager.PROFILE_IDX:
                    toolbar.setTitle("Profile - " + new SavedData(getContext()).getUserName());
                    toolbar.setTitleTextColor(getResources().getColor(R.color.gray900));
                    bottomNavigationView.setSelectedItemId(R.id.action_profile);
                    break;
                case Constant.HomeViewPager.TREASURE_IDX:
                    toolbar.setTitle(R.string.treasures);
                    bottomNavigationView.setSelectedItemId(R.id.action_recent);
                    break;
                case Constant.HomeViewPager.TOPLIST_IDX:
                    toolbar.setTitle(R.string.top_list);
                    bottomNavigationView.setSelectedItemId(R.id.action_favorites);
                    break;
                case 3:
                    toolbar.setTitle(R.string.hide_treasure);
                    bottomNavigationView.setSelectedItemId(R.id.action_hide_treasure);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
