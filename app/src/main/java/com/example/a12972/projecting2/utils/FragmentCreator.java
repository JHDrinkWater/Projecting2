package com.example.a12972.projecting2.utils;

import com.example.a12972.projecting2.base.BaseFragment;
import com.example.a12972.projecting2.fragments.HistoryFragment;
import com.example.a12972.projecting2.fragments.RecommendFragment;
import com.example.a12972.projecting2.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

import static com.example.a12972.projecting2.Constants.Constants.INDEX_HISTORY;
import static com.example.a12972.projecting2.Constants.Constants.INDEX_RECOMMEND;
import static com.example.a12972.projecting2.Constants.Constants.INDEX_SUBSCIPTION;


public class FragmentCreator {
    private static Map<Integer, BaseFragment> sCache = new HashMap();

    // TODO: 2021/7/19 生成Fragment
    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (index) {
            case INDEX_RECOMMEND:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new HistoryFragment();
                break;
            case INDEX_SUBSCIPTION:
                baseFragment = new SubscriptionFragment();
                break;
        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
