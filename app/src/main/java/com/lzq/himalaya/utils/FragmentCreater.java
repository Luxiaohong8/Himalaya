package com.lzq.himalaya.utils;

import com.lzq.himalaya.base.BaseFragment;
import com.lzq.himalaya.fragments.HistoryFragment;
import com.lzq.himalaya.fragments.RecommendFragment;
import com.lzq.himalaya.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreater {
    private static Map<Integer, BaseFragment> sCache=new HashMap<>();

    public final static int INDEX_RECOMMEND = 0;
    public final static int INDEX_SUBSCRIPTION = 1;
    public final static int INDEX_HISTORY = 2;

    public final static int PAGE_COUNT=3;

    public static BaseFragment getFragment(int index){
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (index) {
            case INDEX_RECOMMEND:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment = new SubscriptionFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new HistoryFragment();
                break;
        }

        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
