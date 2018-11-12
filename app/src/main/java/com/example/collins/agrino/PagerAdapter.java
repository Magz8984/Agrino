package com.example.collins.agrino;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import Models.Resource;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numberOfPowers;
    public PagerAdapter(FragmentManager fm,int numberOfPages) {
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
