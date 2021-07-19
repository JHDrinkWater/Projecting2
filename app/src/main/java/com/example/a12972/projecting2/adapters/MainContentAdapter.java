package com.example.a12972.projecting2.adapters;

import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.a12972.projecting2.utils.FragmentCreator;

import static com.example.a12972.projecting2.Constants.Constants.PAGE_COUNT;

// 适配器 供ViewPager使用

public class MainContentAdapter extends FragmentPagerAdapter {
    public MainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCreator.getFragment(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
