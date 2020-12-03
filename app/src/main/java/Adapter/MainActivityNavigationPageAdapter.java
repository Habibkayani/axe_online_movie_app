package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import Fragments.HomeFragment;
import Fragments.MoviesFragment;

import Fragments.TvShowsFragment;

public class MainActivityNavigationPageAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public MainActivityNavigationPageAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new MoviesFragment();
            case 2:
                return new TvShowsFragment();

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
