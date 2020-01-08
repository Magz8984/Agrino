package com.example.collins.agrino;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numberOfPowers;
    public PagerAdapter(FragmentManager fm, int numberOfPages) {
        super(fm);
        this.numberOfPowers=numberOfPages;
    }
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                Services services=new Services();
                return  services;
            case 1:
                Resources resources=new Resources();
                return  resources;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfPowers;
    }
}
