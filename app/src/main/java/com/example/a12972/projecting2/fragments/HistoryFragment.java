package com.example.a12972.projecting2.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a12972.projecting2.R;
import com.example.a12972.projecting2.base.BaseFragment;

public class HistoryFragment extends BaseFragment {
    @Override
    protected View OnSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }
}
