package com.lzq.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.himalaya.R;
import com.lzq.himalaya.base.BaseFragment;

public class HistoryFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootview=layoutInflater.inflate(R.layout.fragment_history,container,false);
        return rootview;
    }
}
