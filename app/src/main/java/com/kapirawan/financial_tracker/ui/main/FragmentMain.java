package com.kapirawan.financial_tracker.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kapirawan.financial_tracker.R;

public class FragmentMain extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_main_fragment,container
                , false);
        ((ViewPager)rootView.findViewById(R.id.viewPager)).setAdapter(
                new PageAdapter(getChildFragmentManager()));
        return rootView;
    }
}