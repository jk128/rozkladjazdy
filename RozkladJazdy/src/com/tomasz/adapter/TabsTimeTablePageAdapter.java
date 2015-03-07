package com.tomasz.adapter;

import com.tomasz.rozkladjazdy.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsTimeTablePageAdapter extends FragmentPagerAdapter {
 
    public TabsTimeTablePageAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new WeekdaysFragment();
        case 1:
            // Games fragment activity
            return new SaturdayFragment();
        case 2:
            // Movies fragment activity
            return new SundayFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}