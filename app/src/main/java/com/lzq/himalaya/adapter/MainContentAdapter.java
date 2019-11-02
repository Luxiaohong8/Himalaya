package com.lzq.himalaya.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lzq.himalaya.utils.FragmentCreater;

public class MainContentAdapter extends FragmentPagerAdapter {
    public MainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCreater.getFragment(position);
    }

    @Override
    public int getCount() {
        return FragmentCreater.PAGE_COUNT;
    }
}
