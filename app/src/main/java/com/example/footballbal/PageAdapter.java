package com.example.footballbal;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int tabOfNum;

    public PageAdapter(@NonNull FragmentManager fm, int tabOfNum) {
        super(fm);
        this.tabOfNum = tabOfNum;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new NewsFragment();
            case 1:
                return new FixtureFragment();
            case 2:
                return new HighlightsFragment();
            case 3:
                return new ChannelFragment();
            case 4:
                return new StatsFragment();
            case 5:
                return new HowFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabOfNum;
    }
}
